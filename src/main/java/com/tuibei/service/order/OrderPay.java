package com.tuibei.service.order;

import com.tuibei.model.order.Order;

public interface OrderPay {

    Order createOrder(Order order) throws Exception;
}
