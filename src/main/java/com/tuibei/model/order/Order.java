package com.tuibei.model.order;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Order {
    private long id;
    private String member_id;//用户ID
    @JsonIgnore
    private String openid;//公众号支付带有open_id
    @JsonIgnore
    private String code;//微信code
    @JsonIgnore
    private String phone;
    private String order_sn;//订单号
    private String goods_id;//商品ID
    private String account;//余额
    private String price;
    private int  status;
    private String remarks;
   private long time;//下单时间
   private int  goods_num=1;//商品数量
   private String goods_name;//商品名
   private long pay_time;//支付时间
   private long confirm_time;//确认时间
   private long refund_time;//退款时间
   private long cancel_time;//取消订单时间
   private int pay_method;// '支付方式   \n0：支付宝\n  1：微信  2：账户余额  3：西安银行',
   private String is_account;//是否使用余额支付，Y是 N否',
   private int origin =1;
   @JsonIgnore
   private String clientIp;//支付ip
    private String pay_type;//支付类型 1 jsapi 2 app 3 h5 4 扫码


    public static void main(String[] args)throws Exception {
        ObjectMapper objectMapper =new ObjectMapper();
        Order order =new Order();
        order.setMember_id("1231313");
        String s = objectMapper.writeValueAsString(order);
        System.out.println(s);
    }
}
