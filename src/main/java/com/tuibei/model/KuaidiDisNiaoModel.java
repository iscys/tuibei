package com.tuibei.model;

import java.util.List;

public class KuaidiDisNiaoModel {

    private  String LogisticCode;//快递号码
    private List<KuaidiNiaoShippers> Shippers; //快递信息
    private String EBusinessID;
    private String Code;//100说明快递查询成功
    private String Success;

    public String getLogisticCode() {
        return LogisticCode;
    }

    public void setLogisticCode(String logisticCode) {
        LogisticCode = logisticCode;
    }

    public List<KuaidiNiaoShippers> getShippers() {
        return Shippers;
    }

    public void setShippers(List<KuaidiNiaoShippers> shippers) {
        Shippers = shippers;
    }

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String success) {
        Success = success;
    }
}
