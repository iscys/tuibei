package com.tuibei.controller.kuaidi;


import com.tuibei.model.Constant;
import com.tuibei.model.KDNTraceScan;
import com.tuibei.model.TraceInfo;
import com.tuibei.service.kuaidi.ExpressService;
import com.tuibei.utils.ResultObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trace")
public class ExpressController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExpressService express;

    /**
     * 快递单号查询
     * @param trackInfo
     * @return
     */
    @PostMapping("/search")
    public ResultObject express_search(TraceInfo trackInfo){
        String trackNum = trackInfo.getTraceNum();
        if(StringUtils.isEmpty(trackNum)){
            logger.error("快递单号为空");
            return ResultObject.build(Constant.TRACE_NUM_NULL,null,Constant.TRACE_NUM_NULL_MESSAGE);
        }
        logger.info("开始查询快递单号为：{} 的信息",trackNum);
        KDNTraceScan scanInfo= express.orderScan(trackInfo);
        return null;
    }
}
