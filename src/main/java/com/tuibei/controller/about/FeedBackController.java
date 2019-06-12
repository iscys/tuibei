package com.tuibei.controller.about;

import com.tuibei.controller.BaseController;
import com.tuibei.model.about.FeedBack;
import com.tuibei.model.constant.Constant;
import com.tuibei.service.about.FeedBackService;
import com.tuibei.utils.ResultObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("about")
public class FeedBackController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private FeedBackService feedBackService;

    @PostMapping("/feedback")
    public ResultObject feedBack(FeedBack feedback){

        if(StringUtils.isEmpty(feedback.getMember_id())){
            return ResultObject.build(Constant.MEMBER_NULL,Constant.MEMBER_NULL_MESSAGE,null);
        }
        if(StringUtils.isEmpty(feedback.getFeedback())){
            return ResultObject.build(Constant.FEED_BACK_NULL,Constant.FEED_BACK_NULL_MESSAGE,null);
        }
        logger.info("开始保存用户：{}反馈信息",feedback.getMember_id());

        return feedBackService.saveFeedBack(feedback);
    }
}
