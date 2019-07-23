package com.tuibei.service.order.impl;

import com.tuibei.model.order.Order;
import com.tuibei.service.order.OrderPay;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 微信公众号支付生成订单
 */
@Service
public class GzhOrderPay implements OrderPay {
    @Autowired
    private WxMpService wxMpService;

    @Override
    public Order createOrder(Order order) throws Exception {
        String code = order.getCode();

        WxMpOAuth2AccessToken tokenInfo =
                        wxMpService.oauth2getAccessToken(code);

        String open_id = tokenInfo.getOpenId();
        order.setOpen_id(open_id);
        return order;
    }
}
