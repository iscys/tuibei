package com.tuibei.controller.kuaidi;


import com.tuibei.model.Constant;
import com.tuibei.model.KuaidiDisNiaoModel;
import com.tuibei.model.TrackInfo;
import com.tuibei.service.kuaidi.ExpressService;
import com.tuibei.utils.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exp")
public class ExpressController {

    @Autowired
    private ExpressService express;

    /**
     * 快递单号查询
     * @param trackInfo
     * @return
     */
    @PostMapping("/search")
    public ResultObject express_search(TrackInfo trackInfo){
        String trackNum = trackInfo.getTrackNum();
        if(StringUtils.isEmpty(trackNum)){
            return ResultObject.build(Constant.TRACK_NUM_NULL,null,Constant.TRACK_NUM_NULL_MESSAGE);
        }
        KuaidiDisNiaoModel scanInfo= express.orderScan(trackInfo);
        return null;
    }
}
