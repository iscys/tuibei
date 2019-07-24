package com.tuibei.service.order;

import com.tuibei.model.order.Order;
import com.tuibei.model.user.User;
import com.tuibei.utils.ResultObject;

import java.util.HashMap;

public interface PrepareOrderService {
    ResultObject createOrder(Order order)throws Exception;

    ResultObject freeDay(User user);

    HashMap<Integer,OrderPay> getOrderPayCache();

}
