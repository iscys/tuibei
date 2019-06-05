package com.tuibei.model;

public class Constant {

    /**
     * 错误码常量
     */
    public static final String TRACE_NUM_NULL ="1001";
    public static final String TRACE_NUM_NULL_MESSAGE ="请输入快递单号";
    public static final String TRACK_NUM_ERROR ="1002";
    public static final String TRACK_NUM_ERROR_MESSAGE ="无此快递";


    /**
     * URL常量
     */
    public static class URL{
        public static final String KDN_SHIPPER_URL ="http://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx";
        public static final String KDN_TRACES_URL ="http://api.kdniao.com/api/dist";
    }

}
