package com.tuibei.service.sms;

import com.tuibei.model.sms.PhoneCode;
import com.tuibei.utils.ResultObject;

public interface AliSmsService {
    ResultObject toSendCode(PhoneCode phoneInfo)throws Exception;
}
