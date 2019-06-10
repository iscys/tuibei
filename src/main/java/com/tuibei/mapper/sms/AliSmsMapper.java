package com.tuibei.mapper.sms;

import com.tuibei.model.sms.PhoneCode;

public interface AliSmsMapper {
    void saveSmsCode(PhoneCode phoneInfo);
}
