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
        //防止恶意接口攻击,距离上一次验证码发送时间不能小于一分钟，即必须等待一分钟才能发送下一个手机验证码
        PhoneCode phoneCode = smsMapper.getRecentPhoneCode(phoneInfo);
        if(null!=phoneCode){
            logger.info("检测验证码发送是否频发 ",phone);
            Long recent_time = phoneCode.getStart_time();
            Long now_time = DateUtils.getTimeInSecond_long();
            Long res =now_time-recent_time;
            if(res<61){
                logger.warn("手机：{} 频繁获取手机验证码，注意！！",phone);
                return ResultObject.build(Constant.PHONE_CODE_BUSY,Constant.PHONE_CODE_BUSY_MESSAGE,null);

            }
        }
        int code = ToolsUtils.fourCode();
        //phoneInfo.setCode(code);
        phoneInfo.setCode(1234);
        phoneInfo.setStart_time(DateUtils.getTimeInSecond_long());
        //有效期间为30分钟
        phoneInfo.setExpire_time(DateUtils.getTimeInSecond_long()+30*60);
        smsMapper.saveSmsCode(phoneInfo);
        logger.info("用户: {} 发送验证码结束",phone);
        return ResultObject.success(null);
    }

}
