package com.tuibei.service.kuaidi.impl;

import com.tuibei.http.KDNHttp;
import com.tuibei.mapper.kuaidi.ExpressMapper;
import com.tuibei.model.KuaidiCommonExtend;
import com.tuibei.model.KuaidiCommonTemplateDetail;
import com.tuibei.model.constant.Constant;
import com.tuibei.model.kdn.*;
import com.tuibei.service.kuaidi.ExpressService;
import com.tuibei.utils.DateUtils;
import com.tuibei.utils.GsonUtils;
import com.tuibei.utils.KudiNiaoMD5Utils;
import com.tuibei.utils.ResultObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;

@Service
public class ExpressServiceImpl implements ExpressService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExpressMapper expressMapper;
    @Value("${kuaidiniao.EBusinessID}")
    private String EBusinessID;
    @Value("${kuaidiniao.AppKey}")
    private String AppKey;

    /**
     * 物流快递公司信息
     * @param trackInfo
     * @return
     * @throws Exception
     */
    @Override
    public ResultObject orderScan(TraceInfo trackInfo)throws Exception {
        String traceNum = trackInfo.getTraceNum();
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
            commonDetail.setCode(Constant.COMMON.UNKNOW);//未知
            commonDetail.setOperator(Constant.COMMON.UNKNOW);//未知
            return ResultObject.success(commonDetail);
        }
        //快递公司code
        String shipperCode = kdnTraceScan.getShippers().get(0).getShipperCode();
        //快递公司名字
        String shipperName = kdnTraceScan.getShippers().get(0).getShipperName();
        commonDetail.setCode(shipperCode);
        commonDetail.setOperator(shipperName);
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

        KuaidiCommonExtend commonDetail =new KuaidiCommonExtend();
        commonDetail.setMember_id(trackInfo.getMember_id());
        String traceNum =trackInfo.getTraceNum();
        //获取单号运营商信息
        String shipperCode = trackInfo.getCode();
        commonDetail.setTraceNum(traceNum);
        commonDetail.setTime(DateUtils.stableTime());
        commonDetail.setCode(shipperCode);
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
