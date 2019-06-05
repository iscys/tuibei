package com.tuibei.model;

/**
 * 快递信息
 */
public class KDNTracesShipper {

    //快递名字
    private String ShipperName;
    //快递代码
    private String ShipperCode;

    public String getShipperName() {
        return ShipperName;
    }

    public void setShipperName(String shipperName) {
        ShipperName = shipperName;
    }

    public String getShipperCode() {
        return ShipperCode;
    }

    public void setShipperCode(String shipperCode) {
        ShipperCode = shipperCode;
    }
}
