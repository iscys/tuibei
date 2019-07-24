package com.tuibei.model.constant;

import java.util.Arrays;
import java.util.List;

public class Constant {

    /**
     * 错误码常量
     */
    public static final String TRACE_NUM_NULL ="1001";
    public static final String TRACE_NUM_NULL_MESSAGE ="请输入快递单号";
    public static final String TRACK_NUM_ERROR ="1002";
    public static final String TRACK_NUM_ERROR_MESSAGE ="没有运营方承接此快递";
    public static final String TRACK_TRACES_ERROR ="1003";
    public static final String TRACK_TRACES_ERROR_MESSAGE ="查询不到快递物流信息";


    public static final String MEMBER_HEADIMGURL_NULL ="1997";
    public static final String MEMBER_HEADIMGURL_NULL_MESSAGE ="用户头像不能为空";
    public static final String MEMBER_NICKNAME_NULL ="1998";
    public static final String MEMBER_NICKNAME_NULL_MESSAGE ="用户昵称不能为空";
    public static final String MEMBER_XXX_NULL ="1999";
    public static final String MEMBER_XXX_NULL_MESSAGE ="无效的用户";
    public static final String MEMBER_NULL ="2000";
    public static final String MEMBER_NULL_MESSAGE ="用户ID 不能为空";
    public static final String PHONE_NULL ="2001";
    public static final String PHONE_NULL_MESSAGE ="手机号码不能为空";
    public static final String PHONE_CODE_NULL ="2002";
    public static final String PHONE_CODE_NULL_MESSAGE ="手机验证码不能为空";
    public static final String PHONE_CODE_ERROR ="2003";
    public static final String PHONE_CODE_ERROR_MESSAGE ="手机验证码错误";
    public static final String WX_CODE_NULL ="2004";
    public static final String WX_CODE_NULL_MESSAGE ="微信code不能为空";
    public static final String PASSWORD_NULL ="2005";
    public static final String PASSWORD_NULL_MESSAGE ="密码不能为空";
    public static final String WX_ERROR ="2006";
    public static final String INVITE_CODE_ERROR ="2007";
    public static final String INVITE_CODE_ERROR_MESSAGE ="无效的推广人";
    public static final String PHONE_EXIST ="2008";
    public static final String PHONE_EXIST_MESSAGE ="用户已经被注册过了";
    public static final String PHONE_ERROR ="2009";
    public static final String PHONE_ERROR_MESSAGE ="输入有效的手机号码";
    public static final String VALIDATE_CODE_TYPE ="2010";
    public static final String VALIDATE_CODE_TYPE_MESSAGE ="无效的验证码类型";
    public static final String ORIGIN_NULL ="2011";
    public static final String ORIGIN_NULL_MESSAGE ="用户来源不能为空";
    public static final String MEMBER_EXIST ="2012";
    public static final String MEMBER_EXIST_MESSAGE ="用户已经存在";
    public static final String PHONE_CODE_BUSY ="2013";
    public static final String PHONE_CODE_BUSY_MESSAGE ="验证码发送频繁";
    public static final String EXPIRE_PHONE_CODE ="2014";
    public static final String EXPIRE_PHONE_CODE_MESSAGE ="无效手机验证码";
    public static final String VALIDATE_MEMBER_ERROR_CODE ="2015";
    public static final String VALIDATE_MEMBER_ERROR_CODE_MESSAGE ="用户名或者密码错误";
    public static final String OPERATION_TYPE_NULL ="2016";
    public static final String OPERATION_TYPE_NULL_MESSAGE ="请选择需要进行操作";
    public static final String SHIP_CODE_NULL ="2017";
    public static final String SHIP_CODE_NULL_MESSAGE ="快递编码不能为空";
    public static final String SHIP_OPERATOR_NULL ="2017";
    public static final String SHIP_OPERATOR_NULL_MESSAGE ="快递公司不能为空";
    public static final String FEED_BACK_NULL ="2018";
    public static final String FEED_BACK_NULL_MESSAGE ="反馈信息不能为空";
    public static final String NO_AUTH_NULL ="2019";
    public static final String NO_AUTH_NULL_MESSAGE ="没有权限操作";
    public static final String HAS_USE_FREE_DAY ="2020";
    public static final String HAS_USE_FREE_DAY_MESSAGE ="已经使用了优惠";
    public static final String SMS_ERROR ="2021";
    public static final String SMS_ERROR_MESSAGE ="发送短信失败";

    public static final String HAS_USE_PRIVILEGE ="2022";
    public static final String HAS_USE_PRIVILEGE_MESSAGE ="已经使用了优惠";

    public static final String VIP_EXPIRE ="2023";
    public static final String VIP_EXPIRE_MESSAGE ="VIP失效,请进行充值";

    public static final String VIP_NO_EXPIRE ="2024";
    public static final String VIP_NO_EXPIRE_MESSAGE ="您已经是VIP会员啦";

    public static final String GOODS_PRICE_NULL ="3000";
    public static final String GOODS_PRICE_NULL_MESSAGE ="商品价钱不能为空";
    public static final String GOODS_PRICE__ERROR ="3001";
    public static final String GOODS_PRICE__ERROR_MESSAGE ="商品价钱错误";
    public static final String GOODS_ID_NULL ="3002";
    public static final String GOODS_ID_NULL_MESSAGE ="商品ID不能为空";



    public static final String ORDERINFO_NULL ="4000";
    public static final String ORDERINFO_NULL_MESSAGE ="无此订单";
    public static final String ORDER_STATUS_ERROR ="4001";
    public static final String ORDER_STATUS_ERROR_MESSAGE ="订单状态错误";
    public static final String ORDER_NULL ="4002";
    public static final String ORDER_NULL_MESSAGE ="订单为空";
    public static final String WX_PAY_EXCEPTION ="4003";
    public static final String WX_PAY_EXCEPTION_MESSAGE ="微信下单异常";
    public static final String WX_PAY_TYPE_NULL ="4004";
    public static final String WX_PAY_TYPE_NULL_MESSAGE ="微信支付类型不能为空";

    public static final String ORDER_ERROR ="4005";
    public static final String ORDER_ERROR_MESSAGE ="订单生成错误";


    /**
     * URL常量
     */
    public static class URL{
        public static final String KDN_SHIPPER_URL ="http://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx";
        //物流跟踪
        public static final String KDN_TRACES_URL ="http://api.kdniao.com/api/dist";
        //及时查询
        public static final String KDN_NOW_SEARCH="http://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx";
    }

    /**
     * 快递鸟指令
     */
    public static class KDN{
        public static final String KDN_TICKET_SCAN ="2002";
        public static final String KDN_TICKET_TRACES ="1002";
        public static final String KDN_JSON_TYPE ="2";

    }

    public static class SMS{
        public static final String PRODUCT ="Dysmsapi";
        public static final String DOMAIN ="dysmsapi.aliyuncs.com";
    }

    public static class COMMON{
        public static final Integer FREEDAY=30;
        public static final String TBKJSUMSCANORDER="TBKJ:SUMSCANORDER";
        public static final String DOMAIN="https://www.tuibeivip.com";
        public static final String UNKNOW ="UNKNOW";
        public static final String DEFAAULTNICKNAME ="退呗科技";
        public static final String DEFAAUL_HEADIMGURL =DOMAIN+"/imgs/defaultHeadImg.jpg";
        public static final Integer PAGESIZE=20;
        public static final List<String> TYPES= Arrays.asList(new String[]{"0","1","2","3"});
    }
}
