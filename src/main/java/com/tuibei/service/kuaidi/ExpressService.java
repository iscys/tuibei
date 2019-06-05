package com.tuibei.service.kuaidi;

import com.tuibei.model.KDNTraceScan;
import com.tuibei.model.TraceInfo;

public interface ExpressService {
    /**
     * 订单识别信息，通过单号查询查询出单号所属的物流公司
     * @param trackInfo
     * @return
     */
    KDNTraceScan orderScan(TraceInfo trackInfo);
}
