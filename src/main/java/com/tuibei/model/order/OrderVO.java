package com.tuibei.model.order;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderVO {
    private long id;
    private String member_id;//用户ID
    private String order_sn;//订单号
    private String goods_id;//商品ID
    //private String account;//余额
    private String price;
    private long time;//下单时间
    private int  goods_num;//商品数量
    private String goods_name;//商品名
    //private int pay_method;// '支付方式   \n0：支付宝\n  1：微信  2：账户余额  3：西安银行',
}
