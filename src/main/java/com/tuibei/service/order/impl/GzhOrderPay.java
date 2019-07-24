package com.tuibei.service.order.impl;

import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.tuibei.config.WxMpProperties;
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
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * 微信公众号支付生成订单
 */
@Service
@EnableConfigurationProperties(WxMpProperties.class)
public class GzhOrderPay implements OrderPay , InitializingBean {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WxMpProperties properties;

    private WxPayService wxPayService;

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

    @Override
    public Object payOrder(Order sourceOrder,Order order) throws Exception {


        String order_sn = order.getOrder_sn();
        String goods_id = order.getGoods_id();
        String openid =order.getOpenid();
        logger.info("订单：{} 开始组装微信支付信息",order_sn);
        WxPayUnifiedOrderRequest payOrder =new WxPayUnifiedOrderRequest();
        payOrder.setSpbillCreateIp(sourceOrder.getClientIp());
        if(sourceOrder.getPay_type().equals("1")) {
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
            wxPackage = wxPayService.createOrder(payOrder);
        } catch (WxPayException e) {
            logger.error("时间：{} ,订单号：{}微信统一下单失败,reason:{}", DateUtils.stableTime(),order_sn,e.getMessage());
            return ResultObject.build(Constant.WX_PAY_EXCEPTION,Constant.WX_PAY_EXCEPTION_MESSAGE,e.getMessage());
        }
        return ResultObject.success(wxPackage);
    }



    @Override
    public void afterPropertiesSet() throws Exception {
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(properties.getAppid());
        payConfig.setMchId(properties.getMchId());
        payConfig.setMchKey(properties.getMchKey());
        payConfig.setKeyPath("classpath:apiclient_cert.p12");
        //payConfig.setKeyPath();
        wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);

    }
}
