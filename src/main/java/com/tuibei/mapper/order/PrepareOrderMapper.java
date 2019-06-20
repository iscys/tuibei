package com.tuibei.mapper.order;

import com.tuibei.model.order.Order;
import com.tuibei.utils.ResultObject;

public interface PrepareOrderMapper {

    void saveOrder(Order order);
    Order getOrderInfo(Order order);
}
