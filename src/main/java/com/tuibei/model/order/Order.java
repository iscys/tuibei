package com.tuibei.model.order;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Order {
    private String member_id;//用户ID
    private String order_sn;//订单号
    private int goods_id;//商品ID
    private String account;//余额
    private String price;
    private int  status;
    private String remarks;
   private long time;//下单时间
   private String  goods_num;//商品数量
   private String goods_name;//商品名
   private long pay_time;//支付时间
   private long confirm_time;//确认时间
   private long refund_time;//退款时间
   private long cancel_time;//取消订单时间
   private int pay_method;// '支付方式   \n0：支付宝\n  1：微信  2：账户余额  3：西安银行',
   private String is_account;//是否使用余额支付，Y是 N否',
   private int origin;

}
