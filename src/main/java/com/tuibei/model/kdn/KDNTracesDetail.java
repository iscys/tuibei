package com.tuibei.model.kdn;

import com.tuibei.model.kdn.KDNTraces;

import java.io.Serializable;
import java.util.List;

/**
 * 快递鸟物流跟踪
 * <a href =http://www.kdniao.com/v2/API/Follow.aspx>
 */
public class KDNTracesDetail implements Serializable {

    private  String LogisticCode;//快递单号
    private List<KDNTraces> Traces; //物流信息
    private String EBusinessID;//快递鸟用户id
    private String Code;//100说明快递查询成功
    private boolean Success;
    private String ShipperCode;//快递编码
    private String Reason;//失败原因
    private Integer State;//物流状态: 0-无轨迹，1-已揽收，2-在途中，3-签收,4-问题件


    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getLogisticCode() {
        return LogisticCode;
    }

    public void setLogisticCode(String logisticCode) {
        LogisticCode = logisticCode;
    }

    public List<KDNTraces> getTraces() {
        return Traces;
    }

    public void setTraces(List<KDNTraces> traces) {
        Traces = traces;
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

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getShipperCode() {
        return ShipperCode;
    }

    public void setShipperCode(String shipperCode) {
        ShipperCode = shipperCode;
    }

    public Integer getState() {
        return State;
    }

    public void setState(Integer state) {
        State = state;
    }
}
