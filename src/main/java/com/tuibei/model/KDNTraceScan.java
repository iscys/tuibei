package com.tuibei.model;

import java.util.List;

public class KDNTraceScan {

    private  String LogisticCode;//快递号码
    private List<KDNTracesShipper> Shippers; //快递信息
    private String EBusinessID;
    private Integer Code;//100说明快递查询成功
    private String Success;

    public String getLogisticCode() {
        return LogisticCode;
    }

    public void setLogisticCode(String logisticCode) {
        LogisticCode = logisticCode;
    }

    public List<KDNTracesShipper> getShippers() {
        return Shippers;
    }

    public void setShippers(List<KDNTracesShipper> shippers) {
        Shippers = shippers;
    }

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer code) {
        Code = code;
    }

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String success) {
        Success = success;
    }
}
