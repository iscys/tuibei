package com.tuibei.controller.sms;

import com.tuibei.model.Constant;
import com.tuibei.model.PhoneCode;
import com.tuibei.utils.ResultObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
public class AliSmsController {

    @RequestMapping("/code")
    public ResultObject code(PhoneCode phoneInfo){
        if(phoneInfo.getType()==null){
            return ResultObject.build(Constant.VALIDATE_CODE_TYPE,Constant.VALIDATE_CODE_TYPE_MESSAGE,null);
        }

        return null;
    }

}
