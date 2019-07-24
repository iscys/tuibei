package com.tuibei.service.order;

import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.tuibei.model.order.Order;
import com.tuibei.model.user.User;

public interface OrderPay {



    Order createOrder(Order order) throws Exception;

    Object payOrder(Order sourceOrder,Order order) throws Exception;

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


    static long switchLevelAndTime(Order orderInfo, User user) {
        String goods_id = orderInfo.getGoods_id();
        long addTime=0;
        switch (goods_id){
            case "1":
                user.setLevel_id("1");
                addTime=30*24*60*60;
                break;
            case "2":
                user.setLevel_id("2");
                addTime=90*24*60*60;
                break;
            case "3":
                user.setLevel_id("3");
                addTime=182*24*60*60;
                break;
            case "4":
                user.setLevel_id("3");
                addTime=365*24*60*60;
                break;

        }

        return addTime;
    }
}
