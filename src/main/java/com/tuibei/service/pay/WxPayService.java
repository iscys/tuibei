package com.tuibei.service.pay;

import com.tuibei.model.order.Order;
import com.tuibei.utils.ResultObject;

public interface WxPayService {
    ResultObject createPay(Order order) throws Exception;
}
