package com.tuibei.model.sms;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class PhoneCode implements Serializable {

    private String id;
    //手机号码
    private String phone;
    //手机验证码
    private int code;
    //开始时间
    private Long start_time;
    //过期时间
    private Long expire_time;
    private String content;
    //发送类型 0 登录 1 注册 2 找回密码 3购买信息
    private Integer type;
    private Integer flag;


}
