package com.tuibei.controller.sms;

import com.tuibei.controller.BaseController;
import com.tuibei.model.constant.Constant;
import com.tuibei.model.sms.PhoneCode;
import com.tuibei.service.sms.AliSmsService;
import com.tuibei.utils.ResultObject;
import com.tuibei.utils.ToolsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/sms")
public class AliSmsController extends BaseController {
        private Logger logger = LoggerFactory.getLogger(this.getClass());
        @Autowired
        private AliSmsService smsService;
    /**
     * 发送验证码
     * @param phoneInfo
     * @return
     */
    @PostMapping("/code")
    public ResultObject code(PhoneCode phoneInfo){
        if(phoneInfo.getType()==null||!Constant.COMMON.TYPES.contains(phoneInfo.getType().toString())){
            return ResultObject.build(Constant.VALIDATE_CODE_TYPE,Constant.VALIDATE_CODE_TYPE_MESSAGE,null);
        }
        if(StringUtils.isEmpty(phoneInfo.getPhone())){
            return  ResultObject.build(Constant.PHONE_NULL,Constant.PHONE_NULL_MESSAGE,null);
        }
        if(!ToolsUtils.checkMobileNumber(phoneInfo.getPhone())){
            return  ResultObject.build(Constant.PHONE_ERROR,Constant.PHONE_ERROR_MESSAGE,null);
        }
        logger.info("开始对手机号：{}进行发送验证,验证码类型为：{}",phoneInfo.getPhone(),phoneInfo.getType());

        try{
           ResultObject result= smsService.toSendCode(phoneInfo);
           return result;
        }catch (Exception e){
            logger.error("发送短信异常：{}",e.getMessage());
            return ResultObject.error(null);
        }

    }

}
