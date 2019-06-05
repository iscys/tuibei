package com.tuibei.model;

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
    private String Success;
    private String ShipperCode;//快递编码
    private Integer State;//物流状态: 0-无轨迹，1-已揽收，2-在途中，3-签收,4-问题件
}
