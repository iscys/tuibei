package com.tuibei.service.order.impl;

import com.tuibei.model.order.Order;
import com.tuibei.service.order.OrderPay;
import com.tuibei.utils.DateUtils;
import com.tuibei.utils.ToolsUtils;
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
        //生成订单号
        String order_sn= DateUtils.getTimeInMillis()+ ToolsUtils.sixCode();
        Long time =DateUtils.getTimeInSecond_long();
        order.setOrder_sn(order_sn);
        order.setTime(time);
        order.setPay_method(1);
        order.setGoods_name("vip 充值");

        String code = order.getCode();

        WxMpOAuth2AccessToken tokenInfo =
                        wxMpService.oauth2getAccessToken(code);

        String openid = tokenInfo.getOpenId();
        order.setOpenid(openid);
        return order;
    }
}
