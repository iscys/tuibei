package com.tuibei.service.kuaidi.impl;

import com.tuibei.http.KDNHttp;
import com.tuibei.model.Constant;
import com.tuibei.model.KDNTraceScan;
import com.tuibei.model.TraceInfo;
import com.tuibei.service.kuaidi.ExpressService;
import com.tuibei.utils.GsonUtils;
import com.tuibei.utils.KudiNiaoMD5Utils;
import com.tuibei.utils.ResultObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

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
        KDNTraceScan kdnTraceScan = this.orderScan(trackInfo);
        if(!kdnTraceScan.isSuccess()||kdnTraceScan.getCode()!=100){
            logger.error("快递鸟查询不出单号：{} 的快递运营方",trackInfo.getTraceNum());
            return ResultObject.build(Constant.TRACK_NUM_ERROR,null,Constant.TRACK_NUM_ERROR_MESSAGE);
        }
        return null;
    }
}
