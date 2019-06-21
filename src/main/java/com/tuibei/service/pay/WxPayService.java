package com.tuibei.service.pay;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.tuibei.model.order.Order;
import com.tuibei.utils.ResultObject;

public interface WxPayService {
    ResultObject createPay(Order order) throws Exception;

    void payNotify(WxPayOrderNotifyResult notifyResult)throws Exception;
}
