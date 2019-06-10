package com.tuibei.model.constant;

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
        public static final String PRODUCT ="2002";
        public static final String DOMAIN ="1002";
    }
}
