package com.tuibei.service.order;

import com.tuibei.model.order.Order;
import com.tuibei.utils.ResultObject;

public interface PrepareOrderService {
    ResultObject createOrder(Order order)throws Exception;
}
