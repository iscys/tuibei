package com.tuibei.service.sms.impl;

import com.tuibei.mapper.sms.AliSmsMapper;
import com.tuibei.mapper.user.UserMapper;
import com.tuibei.model.constant.Constant;
import com.tuibei.model.sms.PhoneCode;
import com.tuibei.model.user.User;
import com.tuibei.service.sms.AliSmsService;
import com.tuibei.utils.ResultObject;
import com.tuibei.utils.ToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AliSmsServiceImpl implements AliSmsService {

    
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
        //type =1 是注册用户
        if(phoneInfo.getType()==1){
            User user =new User();
            user.setPhone(phoneInfo.getPhone());
            User isExist = userMapper.getUserInfo(user);
            if(null!=isExist){
                return ResultObject.build(Constant.MEMBER_EXIST,Constant.MEMBER_EXIST_MESSAGE,null);
            }
            int code = ToolsUtils.fourCode();
            phoneInfo.setCode(code);


        }
           
            

        return null;
    }
}
