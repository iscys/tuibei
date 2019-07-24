package com.tuibei.service.order;

import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.tuibei.model.order.Order;

public interface OrderPay {

    Order createOrder(Order order) throws Exception;

    Object payOrder(Order order) throws Exception;

    static void switchOrderType(Order order,WxPayUnifiedOrderRequest req){
        String goods_id = order.getGoods_id();
        switch (goods_id){
            case "1":
                req.setBody("月卡");
                break;
            case "2":
                req.setBody("季卡");
                break;
            case "3":
                req.setBody("半年卡");
                break;
            case "4":
                req.setBody("年会员");
                break;

        }
    }
}
