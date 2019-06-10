package com.tuibei.service.sms.impl;

import com.tuibei.model.sms.PhoneCode;
import com.tuibei.service.sms.AliSmsService;
import com.tuibei.utils.ResultObject;
import org.springframework.stereotype.Service;

@Service
public class AliSmsServiceImpl implements AliSmsService {

    @Override
    public ResultObject toSendCode(PhoneCode phoneInfo) throws Exception {
        return null;
    }
}
