package com.tuibei.service.order.impl;

import com.tuibei.mapper.user.UserMapper;
import com.tuibei.model.order.Order;
import com.tuibei.model.user.User;
import com.tuibei.service.order.OrderPay;
import com.tuibei.utils.DateUtils;
import com.tuibei.utils.ToolsUtils;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 微信公众号支付生成订单
 */
@Service
public class GzhOrderPay implements OrderPay {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Order createOrder(Order order) throws Exception {

        String phone = order.getPhone();
        User tmpUser =new User();
        tmpUser.setPhone(phone);
        User simpleUser =userMapper.getSimpleUserInfo(tmpUser);
        order.setPhone(simpleUser.getPhone());
        order.setOpenid(simpleUser.getOpenid());
        order.setMember_id(simpleUser.getMember_id());

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

        logger.info("组装来自微信小程序的订单成功，订单：{} ,手机：{},用户:{}"
                ,order_sn,simpleUser.getPhone(),order.getMember_id());

        return order;
    }
}
