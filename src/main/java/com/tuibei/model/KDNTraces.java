package com.tuibei.model;

import java.io.Serializable;

/**
 * 快递鸟物流信息
 */
public class KDNTraces implements Serializable {


    private String AcceptStation;//快件描述
    private String AcceptTime;//快件时间

    public String getAcceptStation() {
        return AcceptStation;
    }

    public void setAcceptStation(String acceptStation) {
        AcceptStation = acceptStation;
    }

    public String getAcceptTime() {
        return AcceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        AcceptTime = acceptTime;
    }
}
