package com.tuibei.service.sms.impl;

import com.tuibei.mapper.sms.AliSmsMapper;
import com.tuibei.mapper.user.UserMapper;
import com.tuibei.model.constant.Constant;
import com.tuibei.model.sms.PhoneCode;
import com.tuibei.model.user.User;
import com.tuibei.service.sms.AliSmsService;
import com.tuibei.utils.DateUtils;
import com.tuibei.utils.ResultObject;
import com.tuibei.utils.ToolsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AliSmsServiceImpl implements AliSmsService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    
    @Autowired
    private AliSmsMapper smsMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     *
     * 发送验证码
     * @param phoneInfo
     * @return
     * @throws Exception
     */
    @Override
    public ResultObject toSendCode(PhoneCode phoneInfo) throws Exception {
        String phone = phoneInfo.getPhone();
        logger.info("用户: {} 发送验证码开始",phone);
        //type =1 是注册用户
        if(phoneInfo.getType()==1){
            User user =new User();
            user.setPhone(phoneInfo.getPhone());
            User isExist = userMapper.getUserInfo(user);
            user =null;//help gc
            if(null!=isExist){
                logger.warn("用户已经被注册过了：{} ",phone);
                return ResultObject.build(Constant.MEMBER_EXIST,Constant.MEMBER_EXIST_MESSAGE,null);
            }

        }

        int code = ToolsUtils.fourCode();
        phoneInfo.setCode(code);
        phoneInfo.setStart_time(DateUtils.getTimeInSecond());
        smsMapper.saveSmsCode(phoneInfo);
        logger.info("用户: {} 发送验证码结束",phone);
           
            

        return null;
    }
}
