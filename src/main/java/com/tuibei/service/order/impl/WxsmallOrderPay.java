package com.tuibei.service.order.impl;

import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.tuibei.mapper.user.UserMapper;
import com.tuibei.model.constant.Constant;
import com.tuibei.model.order.Order;
import com.tuibei.model.user.User;
import com.tuibei.service.order.OrderPay;
import com.tuibei.utils.DateUtils;
import com.tuibei.utils.ResultObject;
import com.tuibei.utils.ToolsUtils;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 微信小程序生成订单
 */
@Service
public class WxsmallOrderPay implements OrderPay {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WxPayService  wxPay;


    @Override
    public Order createOrder(Order order) throws Exception {
        String member_id = order.getMember_id();
        User tmpUser =new User();
        tmpUser.setMember_id(member_id);
        User simpleUser =userMapper.getSimpleUserInfo(tmpUser);

        order.setPhone(simpleUser.getPhone());
        order.setOpenid(simpleUser.getOpenid());

        //生成订单号
        String order_sn= DateUtils.getTimeInMillis()+ ToolsUtils.sixCode();
        Long time =DateUtils.getTimeInSecond_long();
        order.setOrder_sn(order_sn);
        order.setTime(time);
        order.setPay_method(1);
        order.setGoods_name("vip 充值");
        logger.info("组装来自微信小程序的订单成功，订单：{} ,手机：{},用户:{}"
        ,order_sn,simpleUser.getPhone(),order.getMember_id());

        return order;
    }

    /**
     * 小程序充值
     * @param order
     * @return
     * @throws Exception
     */
    @Override
    public Object payOrder(Order order) throws Exception {

        String order_sn = order.getOrder_sn();
        String goods_id = order.getGoods_id();
        String openid =order.getOpenid();
        logger.info("订单：{} 开始组装微信支付信息",order_sn);
        WxPayUnifiedOrderRequest payOrder =new WxPayUnifiedOrderRequest();
        payOrder.setSpbillCreateIp(order.getClientIp());
        if(order.getPay_type().equals("1")) {
            payOrder.setTradeType(WxPayConstants.TradeType.JSAPI);//小程序公众号支付
        }
        payOrder.setOutTradeNo(order.getOrder_sn());
        payOrder.setOpenid(openid);
        payOrder.setNotifyUrl(Constant.COMMON.DOMAIN+"/wx/notify");
        OrderPay.switchOrderType(order,payOrder);
        payOrder.setAttach(openid);
        payOrder.setTotalFee(BaseWxPayRequest.yuanToFen(order.getPrice()));
        Object wxPackage = null;
        try {
            wxPackage = wxPay.createOrder(payOrder);
        } catch (WxPayException e) {
            logger.error("时间：{} ,订单号：{}微信统一下单失败,reason:{}", DateUtils.stableTime(),order_sn,e.getMessage());
            return ResultObject.build(Constant.WX_PAY_EXCEPTION,Constant.WX_PAY_EXCEPTION_MESSAGE,e.getMessage());
        }
        return ResultObject.success(wxPackage);
    }
}
