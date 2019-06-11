package com.tuibei.service.kuaidi;

import com.tuibei.model.kdn.KDNTraceScan;
import com.tuibei.model.kdn.TraceInfo;
import com.tuibei.utils.ResultObject;

public interface ExpressService {
    /**
     * 订单识别信息，通过单号查询查询出单号所属的物流公司
     * @param trackInfo
     * @return
     */
    ResultObject orderScan(TraceInfo trackInfo)throws Exception;

    ResultObject traceDetail(TraceInfo trackInfo)throws Exception;
}
