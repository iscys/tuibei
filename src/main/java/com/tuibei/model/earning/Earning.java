package com.tuibei.model.earning;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Earning {

    private double earning;//收益钱数
    private String earning_member_id;//收益人的唯一ID
    private String order_sn;//收益的订单ç
    private String order_member_id;//支付订单的人员
    private String goods_id;//商品ID

}
