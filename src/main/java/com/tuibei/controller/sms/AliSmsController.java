package com.tuibei.controller.sms;

import com.tuibei.model.PhoneCode;
import com.tuibei.utils.ResultObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
public class AliSmsController {

    @RequestMapping("/code")
    public ResultObject code(PhoneCode phoneInfo){


        return null;
    }

}
