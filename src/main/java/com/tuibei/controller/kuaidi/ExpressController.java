package com.tuibei.controller.kuaidi;


import com.tuibei.model.constant.Constant;
import com.tuibei.model.kdn.KDNTraceScan;
import com.tuibei.model.kdn.TraceInfo;
import com.tuibei.model.user.User;
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
     * @param trackInfo user
     * @return
     */
    @PostMapping("/search")
    public ResultObject express_search(TraceInfo trackInfo) {
        String trackNum = trackInfo.getTraceNum();
        if(StringUtils.isEmpty(trackNum)){
            logger.error("快递单号为空");
            return ResultObject.build(Constant.TRACE_NUM_NULL,Constant.TRACE_NUM_NULL_MESSAGE,null);
        }
        logger.info("开始查询快递单号为：{} 的信息",trackNum);
            try {
                ResultObject result = express.traceDetail(trackInfo);
                return  result;
            }catch (Exception e){
                logger.error("系统异常发生：{}",e.getMessage());
                return ResultObject.error(null);
            }

    }

}
