package com.tuibei.service.about.impl;

import com.tuibei.mapper.about.FeedBackMapper;
import com.tuibei.mapper.user.UserMapper;
import com.tuibei.model.about.FeedBack;
import com.tuibei.model.constant.Constant;
import com.tuibei.model.user.User;
import com.tuibei.service.about.FeedBackService;
import com.tuibei.utils.ResultObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FeedBackServiceImpl implements FeedBackService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private FeedBackMapper feedBackMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResultObject saveFeedBack(FeedBack feedback) {
        User user =new User();
        user.setMember_id(feedback.getMember_id());
        User userInfo = userMapper.getUserInfo(user);
        if(null==userInfo){
            return ResultObject.build(Constant.MEMBER_XXX_NULL,Constant.MEMBER_XXX_NULL_MESSAGE,null);
        }
        feedBackMapper.saveFeedBack(feedback);
        return ResultObject.success(null);

    }
}
