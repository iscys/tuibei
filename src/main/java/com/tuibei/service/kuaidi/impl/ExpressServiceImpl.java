package com.tuibei.service.kuaidi.impl;

import com.tuibei.model.KuaidiDisNiaoModel;
import com.tuibei.model.TraceInfo;
import com.tuibei.service.kuaidi.ExpressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExpressServiceImpl implements ExpressService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public KuaidiDisNiaoModel orderScan(TraceInfo trackInfo) {
        logger.info("查询快递单号识别：{} ",trackInfo.getTraceNum());
        return null;
    }
}
