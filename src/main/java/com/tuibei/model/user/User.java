package com.tuibei.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ToString
@Builder
@NoArgsConstructor
public class User implements Serializable {
    @JsonIgnore
    //主键id
    private Integer id;
    //性别
    private String sex;
    //用户唯一的 member_id
    private String member_id;
    //昵称
    private String nickname;
    //用户姓名
    private String username;

    //用户手机
    private String phone;
    @JsonIgnore
    //用户密码
    private String password;
    //openid
    private String openid;
    //unionid
    private String unionid;
    @JsonIgnore
    //微信code
    private String code;
    //用户头像
    private String headimgurl;
    //邀请码,每一个用户会拥有一个邀请码
    private String invite_code;
    //用户来源
    private String origin;//0 为app 来源 1 为小程序来源 2-公众号来源
    //用户等级
    private String level_id;
    //用户上一级member_id
    private String master;
    @JsonIgnore
    //手机验证码
    private String phone_code;
    @JsonIgnore
    //关注公众号时间
    private String time;
    //vip 失效时间
    private  String vip_expire_time;
    //账户余额
    private double account=0.00;
    @JsonIgnore
    //用户最后一次登录的ip
    private String last_ip;
    @JsonIgnore
    //用户最后一次登录时间
    private String last_login;
    private int use_free;

    private int invite_count;


}
