package com.tuibei.service.kuaidi.impl;

import com.tuibei.http.KDNHttp;
import com.tuibei.mapper.kuaidi.ExpressMapper;
import com.tuibei.model.ExpressRecord;
import com.tuibei.model.KuaidiCommonExtend;
import com.tuibei.model.KuaidiCommonTemplateDetail;
import com.tuibei.model.constant.Constant;
import com.tuibei.model.kdn.*;
import com.tuibei.service.kuaidi.ExpressService;
import com.tuibei.utils.DateUtils;
import com.tuibei.utils.GsonUtils;
import com.tuibei.utils.KudiNiaoMD5Utils;
import com.tuibei.utils.ResultObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ExpressServiceImpl implements ExpressService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExpressMapper expressMapper;
    @Value("${kuaidiniao.EBusinessID}")
    private String EBusinessID;
    @Value("${kuaidiniao.AppKey}")
    private String AppKey;


    @Autowired
    private StringRedisTemplate template;

    /**
     * 物流快递公司信息
     * @param trackInfo
     * @return
     * @throws Exception
     */
    @Override
    public ResultObject orderScan(TraceInfo trackInfo)throws Exception {
        String traceNum = trackInfo.getTraceNum();
        try {
            //计入统计redis,不影响业务执行 redis  setnx 不存在插入的操作
            template.opsForValue().setIfAbsent(Constant.COMMON.TBKJSUMSCANORDER, "0");
            template.opsForValue().increment(Constant.COMMON.TBKJSUMSCANORDER);
            String result = template.opsForValue().get(traceNum);
            if(StringUtils.isNotEmpty(result)) {
                logger.info("redis 里获取数据缓存：{}",result);
                KuaidiCommonTemplateDetail commonDetail = GsonUtils.fromJson(result, KuaidiCommonTemplateDetail.class);
                return ResultObject.success(commonDetail);
            }

        }catch (Exception e){
            logger.error("redis 错误：{}",e.getMessage());
        }

        logger.info("开始查询快递单号识别：{} ",traceNum);
        String requestData= "{'LogisticCode':'" + traceNum + "'}";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("RequestData", KudiNiaoMD5Utils.urlEncoder(requestData, "UTF-8"));
        params.put("EBusinessID", EBusinessID);
        params.put("RequestType", Constant.KDN.KDN_TICKET_SCAN);
        String dataSign= KudiNiaoMD5Utils.encrypt(requestData, AppKey, "UTF-8");
        params.put("DataSign", KudiNiaoMD5Utils.urlEncoder(dataSign, "UTF-8"));
        params.put("DataType", Constant.KDN.KDN_JSON_TYPE);
        String result = KDNHttp.INSTANCE.doPost(Constant.URL.KDN_SHIPPER_URL, params);
        logger.info("快递单号：{} 识别信息为：{} ",traceNum,result);
        KDNTraceScan kdnTraceScan = GsonUtils.fromJson(result, KDNTraceScan.class);

        KuaidiCommonTemplateDetail commonDetail =new KuaidiCommonTemplateDetail();
        commonDetail.setTraceNum(traceNum);
        commonDetail.setTime(DateUtils.stableTime());
        List<KDNTracesShipper> shippers = kdnTraceScan.getShippers();
        if(!kdnTraceScan.isSuccess()||kdnTraceScan.getCode()!=100|| CollectionUtils.isEmpty(shippers)){
            logger.error("快递鸟查询不出单号：{} 的快递运营方",traceNum);
            //return ResultObject.build(Constant.TRACK_NUM_ERROR,Constant.TRACK_NUM_ERROR_MESSAGE,commonDetail);
            commonDetail.setShip_code(Constant.COMMON.UNKNOW);//未知
            commonDetail.setOperator(Constant.COMMON.UNKNOW);//未知
            return ResultObject.build(Constant.TRACK_NUM_ERROR,Constant.TRACK_NUM_ERROR_MESSAGE,commonDetail);
        }
        //快递公司code
        String shipperCode = kdnTraceScan.getShippers().get(0).getShipperCode();
        //快递公司名字
        String shipperName = kdnTraceScan.getShippers().get(0).getShipperName();
        commonDetail.setShip_code(shipperCode);
        commonDetail.setOperator(shipperName);
        try {
            template.opsForValue().set(traceNum, GsonUtils.toJson(commonDetail), 6, TimeUnit.MINUTES);
        }catch (Exception e){
            logger.error("redis 错误：{}",e.getMessage());
        }
        return ResultObject.success(commonDetail);
    }

    /**
     * 获取物流轨迹信息
     * @param trackInfo
     * @return
     * @throws Exception
     */
    @Override
    public ResultObject traceDetail(TraceInfo trackInfo) throws Exception {
        logger.info("用户：{} 查询单号：{} 的物流信息",trackInfo.getMember_id(),trackInfo.getTraceNum());
        KuaidiCommonExtend commonDetail =new KuaidiCommonExtend();
        ExpressRecord record =new ExpressRecord();
        record.setMember_id(trackInfo.getMember_id());
        record.setTraceNum(trackInfo.getTraceNum());
        commonDetail.setOperator(trackInfo.getOperator());
        ExpressRecord expressRecord=expressMapper.getExpressRecord(record);
        commonDetail.setMember_id(trackInfo.getMember_id());
        record=null;
        if(null!=expressRecord){
            commonDetail.setOperation_type(expressRecord.getOperation_type());
            commonDetail.setTime(expressRecord.getOperation_time());
            commonDetail.setRemark(expressRecord.getRemark());
            commonDetail.setMember_id(expressRecord.getMember_id());
        }


        String traceNum =trackInfo.getTraceNum();
        //获取单号运营商信息
        String shipperCode = trackInfo.getShip_code();
        commonDetail.setTraceNum(traceNum);
        if(null==expressRecord) {
            commonDetail.setTime(DateUtils.stableTime());
        }
        commonDetail.setShip_code(shipperCode);
        //组装参数信息
        HashMap<String,String> shipperInfo =new HashMap<String,String>();
        shipperInfo.put("ShipperCode",shipperCode);
        shipperInfo.put("LogisticCode",traceNum);
        shipperInfo.put("OrderCode","");
        String requestData = GsonUtils.toJson(shipperInfo);//json数据
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("RequestData", KudiNiaoMD5Utils.urlEncoder(requestData, "UTF-8"));
        params.put("EBusinessID", EBusinessID);
        params.put("RequestType", Constant.KDN.KDN_TICKET_TRACES);//指令
        String dataSign= KudiNiaoMD5Utils.encrypt(requestData, AppKey, "UTF-8");
        params.put("DataSign", KudiNiaoMD5Utils.urlEncoder(dataSign, "UTF-8"));
        params.put("DataType", Constant.KDN.KDN_JSON_TYPE);
        String traces = KDNHttp.INSTANCE.doPost(Constant.URL.KDN_TRACES_URL, params);
        logger.info("快递鸟返回物流信息：{}",traces);
        KDNTracesDetail kdnTracesDetail = GsonUtils.fromJson(traces, KDNTracesDetail.class);
        if(kdnTracesDetail.isSuccess()){
            commonDetail.KDN2Common(kdnTracesDetail);
        }else{
            commonDetail.setState("无轨迹");
            logger.error("物流单号：{}获取物流信息失败,error:{}",traceNum,kdnTracesDetail.getReason());
            return ResultObject.success(commonDetail);
        }

        return ResultObject.success(commonDetail);
    }
}
