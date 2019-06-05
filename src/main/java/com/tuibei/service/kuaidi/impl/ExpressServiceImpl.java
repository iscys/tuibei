package com.tuibei.service.kuaidi.impl;

import com.tuibei.http.KDNHttp;
import com.tuibei.model.*;
import com.tuibei.service.kuaidi.ExpressService;
import com.tuibei.utils.GsonUtils;
import com.tuibei.utils.KudiNiaoMD5Utils;
import com.tuibei.utils.ResultObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpressServiceImpl implements ExpressService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${kuaidiniao.EBusinessID}")
    private String EBusinessID;
    @Value("${kuaidiniao.AppKey}")
    private String AppKey;
    @Override
    public KDNTraceScan orderScan(TraceInfo trackInfo)throws Exception {
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
        return kdnTraceScan;
    }

    /**
     * 获取物流信息
     * @param trackInfo
     * @return
     * @throws Exception
     */
    @Override
    public ResultObject traceDetail(TraceInfo trackInfo) throws Exception {

        KuaidiCommonTemplateDetail commonDetail =new KuaidiCommonTemplateDetail();
        String traceNum =trackInfo.getTraceNum();
        KDNTraceScan kdnTraceScan = this.orderScan(trackInfo);
        List<KDNTracesShipper> shippers = kdnTraceScan.getShippers();
        if(!kdnTraceScan.isSuccess()||kdnTraceScan.getCode()!=100|| CollectionUtils.isEmpty(shippers)){
            logger.error("快递鸟查询不出单号：{} 的快递运营方",traceNum);
            return ResultObject.build(Constant.TRACK_NUM_ERROR,null,Constant.TRACK_NUM_ERROR_MESSAGE);
        }
        //快递公司code
        String shipperCode = kdnTraceScan.getShippers().get(0).getShipperCode();
        //快递公司名字
        String shipperName = kdnTraceScan.getShippers().get(0).getShipperName();
        logger.info("得到单号：{} 的运营方信息，运营方code:{},运营方名字：{}",traceNum,shipperCode,shipperName);

        //组装参数信息
        HashMap<String,String> shipperInfo =new HashMap<String,String>();
        shipperInfo.put("ShipperCode",shipperCode);
        shipperInfo.put("LogisticCode",traceNum);
        shipperInfo.put("OrderCode","");
        String requestData = GsonUtils.toJson(shipperInfo);//json数据
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("RequestData", KudiNiaoMD5Utils.urlEncoder(requestData, "UTF-8"));
        params.put("EBusinessID", EBusinessID);
        params.put("RequestType", "1002");
        String dataSign= KudiNiaoMD5Utils.encrypt(requestData, AppKey, "UTF-8");
        params.put("DataSign", KudiNiaoMD5Utils.urlEncoder(dataSign, "UTF-8"));
        params.put("DataType", Constant.KDN.KDN_JSON_TYPE);
        String traces = KDNHttp.INSTANCE.doPost(Constant.URL.KDN_TRACES_URL, params);
        logger.info("快递鸟返回物流信息：{}",traces);
        KDNTracesDetail kdnTracesDetail = GsonUtils.fromJson(traces, KDNTracesDetail.class);
        if(kdnTracesDetail.isSuccess()){
            commonDetail.conv2Common(kdnTracesDetail);
        }else{
            return ResultObject.build(Constant.TRACK_TRACES_ERROR,null,Constant.TRACK_TRACES_ERROR_MESSAGE);
        }
        commonDetail.setTraceNum(traceNum);
        commonDetail.setOperator(shipperName);
        return ResultObject.success(commonDetail);
    }
}
