package com.tuibei.mapper.sms;

import com.tuibei.model.sms.PhoneCode;

public interface AliSmsMapper {
    //发送验证码
    void saveSmsCode(PhoneCode phoneInfo);
    //获取最近一次的验证码记录
    PhoneCode getRecentPhoneCode(PhoneCode phoneInfo);
}
