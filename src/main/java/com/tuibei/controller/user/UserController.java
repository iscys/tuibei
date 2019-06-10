package com.tuibei.controller.user;

import com.tuibei.model.constant.Constant;
import com.tuibei.model.user.User;
import com.tuibei.service.user.UserService;
import com.tuibei.utils.ResultObject;
import com.tuibei.utils.ToolsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usr")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;
    @PostMapping("/regist")
    public ResultObject registry(User user){

        if(StringUtils.isEmpty(user.getPhone())){
          return  ResultObject.build(Constant.PHONE_NULL,Constant.PHONE_NULL_MESSAGE,null);
        }
        if(!ToolsUtils.checkMobileNumber(user.getPhone())){
            return  ResultObject.build(Constant.PHONE_ERROR,Constant.PHONE_ERROR_MESSAGE,null);
        }
        logger.info("用户：{} 进行注册",user.getPhone());
        if(StringUtils.isEmpty(user.getPhone_code())){
            return  ResultObject.build(Constant.PHONE_CODE_NULL,Constant.PHONE_CODE_NULL_MESSAGE,null);
        }
        if(StringUtils.isEmpty(user.getCode())){
            return  ResultObject.build(Constant.WX_CODE_NULL,Constant.WX_CODE_NULL_MESSAGE,null);
        }
        if(StringUtils.isEmpty(user.getPassword())){
            return  ResultObject.build(Constant.PASSWORD_NULL,Constant.PASSWORD_NULL_MESSAGE,null);
        }

        try {
            ResultObject result = userService.toRegistry(user);
            return result;
        }catch (Exception e){
            logger.error("用户注册异常：{}" ,e.getMessage());
            return ResultObject.error(null);
        }

    }
}
