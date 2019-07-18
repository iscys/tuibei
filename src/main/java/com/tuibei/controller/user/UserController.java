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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@RestController
@RequestMapping("/usr")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;


    @PostMapping("/regist")
    public ResultObject registry(User user,HttpServletRequest request){

        String ip= ToolsUtils.getClientIp(request);
        logger.info("用户ip 为：{}" ,ip);
        user.setLast_ip(ip);
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
        if(StringUtils.isEmpty(user.getOrigin())){
            return  ResultObject.build(Constant.ORIGIN_NULL,Constant.ORIGIN_NULL_MESSAGE,null);
        }

        try {
            ResultObject result = userService.toRegistry(user);
            return result;
        }catch (Exception e){
            logger.error("用户注册异常：{}" ,e.getMessage());
            return ResultObject.error(null);
        }

    }

    @PostMapping("/login")
    public ResultObject login(User user,HttpServletRequest request){
        String ip= ToolsUtils.getClientIp(request);
        logger.info("用户ip 为：{}" ,ip);
        user.setLast_ip(ip);
        if(StringUtils.isEmpty(user.getPhone())){
            return  ResultObject.build(Constant.PHONE_NULL,Constant.PHONE_NULL_MESSAGE,null);
        }
        if(!ToolsUtils.checkMobileNumber(user.getPhone())){
            return  ResultObject.build(Constant.PHONE_ERROR,Constant.PHONE_ERROR_MESSAGE,null);
        }
        if(StringUtils.isEmpty(user.getPassword())){
            return  ResultObject.build(Constant.PASSWORD_NULL,Constant.PASSWORD_NULL_MESSAGE,null);
        }

        try{
            ResultObject result = userService.toLogin(user);
            return result;

        }catch(Exception e){
            logger.error("登录异常：{}" ,e.getMessage());
            return ResultObject.error(null);
        }

    }

    /**
     *修改用户密码
     * --使用验证码
     * @param user
     * @return
     */
    @PostMapping("/modifyPassword")
    public ResultObject modify(User user){
        if(StringUtils.isEmpty(user.getPhone())){
            return  ResultObject.build(Constant.PHONE_NULL,Constant.PHONE_NULL_MESSAGE,null);
        }
        if(!ToolsUtils.checkMobileNumber(user.getPhone())){
            return  ResultObject.build(Constant.PHONE_ERROR,Constant.PHONE_ERROR_MESSAGE,null);
        }
        if(StringUtils.isEmpty(user.getPassword())){
            return  ResultObject.build(Constant.PASSWORD_NULL,Constant.PASSWORD_NULL_MESSAGE,null);
        }
        if(StringUtils.isEmpty(user.getPhone_code())){
            return  ResultObject.build(Constant.PHONE_CODE_NULL,Constant.PHONE_CODE_NULL_MESSAGE,null);
        }

        try{
            ResultObject result = userService.toModify(user);
            return result;

        }catch(Exception e){
            logger.error("登录异常：{}" ,e.getMessage());
            return ResultObject.error(null);
        }

    }

    /**
     * 修改用户个人资料信息
     * @param user
     * @return
     */
    @PostMapping("/modifySelf")
    public ResultObject modifySelf(User user){


        if(StringUtils.isEmpty(user.getMember_id())){
            return  ResultObject.build(Constant.MEMBER_NULL,Constant.MEMBER_NULL_MESSAGE,null);
        }
        if(StringUtils.isEmpty(user.getNickname())){
            return  ResultObject.build(Constant.MEMBER_NICKNAME_NULL,Constant.MEMBER_NICKNAME_NULL_MESSAGE,null);
        }
        if(StringUtils.isEmpty(user.getHeadimgurl())){
            return  ResultObject.build(Constant.MEMBER_HEADIMGURL_NULL,Constant.MEMBER_HEADIMGURL_NULL_MESSAGE,null);
        }

        try{
            ResultObject result = userService.toModifySelf(user);
            return result;

        }catch(Exception e){
            logger.error("登录异常：{}" ,e.getMessage());
            return ResultObject.error(null);
        }

    }


    @PostMapping("/info")
    public ResultObject info(User user,HttpServletRequest request){

        if(StringUtils.isEmpty(user.getMember_id())){
            return  ResultObject.build(Constant.MEMBER_NULL,Constant.MEMBER_NULL_MESSAGE,null);
        }

        try{
            ResultObject result = userService.getUserInfo(user);
            return result;

        }catch(Exception e){
            logger.error("获取用户信息异常：{}" ,e.getMessage());
            return ResultObject.error(null);
        }

    }

    @PostMapping("/expire")
    public ResultObject expire(User user,HttpServletRequest request){

        if(StringUtils.isEmpty(user.getMember_id())){
            return  ResultObject.build(Constant.MEMBER_NULL,Constant.MEMBER_NULL_MESSAGE,null);
        }

        try{
            ResultObject result = userService.getExpireTime(user);
            return result;

        }catch(Exception e){
            logger.error("获取用户失效日期异常：{}" ,e.getMessage());
            return ResultObject.error(null);
        }

    }
}
