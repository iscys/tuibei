package com.tuibei.service.sms.impl;

import com.tuibei.mapper.sms.AliSmsMapper;
import com.tuibei.mapper.user.UserMapper;
import com.tuibei.model.constant.Constant;
import com.tuibei.model.sms.PhoneCode;
import com.tuibei.model.sms.SmsContent;
import com.tuibei.model.user.User;
import com.tuibei.service.sms.AliSmsService;
import com.tuibei.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@Transactional
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
            User user =new User();
            user.setPhone(phoneInfo.getPhone());
            User isExist = userMapper.getUserInfo(user);
            user =null;//help gc
        if(phoneInfo.getType()==1) {
            //type =1 是注册用户
            if (null != isExist) {
                logger.warn("用户已经被注册过了：{} ", phone);
                return ResultObject.build(Constant.MEMBER_EXIST, Constant.MEMBER_EXIST_MESSAGE, null);
            }
        }else{
            if (null == isExist) {
                logger.warn("用户尚未被注册：{} ", phone);
                return ResultObject.build(Constant.MEMBER_XXX_NULL, Constant.MEMBER_XXX_NULL_MESSAGE, null);
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
        SmsContent content =new SmsContent();
        content.setPhone(phone);
        content.setTemplateCode(AliSmsUtils.getInstance().getPropertiesValue("alibaba.sms.templateLogin"));
        content.setSignName("退呗验证码");
        HashMap<String,Integer> map =new HashMap<>();
        map.put("code",code);
        content.setTemplateParam(GsonUtils.toJson(map));


        String resultCode = AliSmsUtils.getInstance().sendSms(content);
        System.out.println(resultCode);
        if(!resultCode.equals("OK")){
            logger.error("发送手机验证码失败，手机:{}",phone);
            return ResultObject.build(Constant.SMS_ERROR,Constant.SMS_ERROR_MESSAGE,null);
        }
        phoneInfo.setCode(code);
        phoneInfo.setStart_time(DateUtils.getTimeInSecond_long());
        //有效期间为30分钟
        phoneInfo.setExpire_time(DateUtils.getTimeInSecond_long()+15*60);
        smsMapper.saveSmsCode(phoneInfo);
        logger.info("用户: {} 发送验证码结束",phone);
        return ResultObject.success(null);
    }

}
