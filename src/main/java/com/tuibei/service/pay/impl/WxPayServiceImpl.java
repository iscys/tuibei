package com.tuibei.service.pay.impl;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.tuibei.mapper.order.PrepareOrderMapper;
import com.tuibei.mapper.user.UserMapper;
import com.tuibei.model.constant.Constant;
import com.tuibei.model.order.Order;
import com.tuibei.model.user.User;
import com.tuibei.model.user.VipModel;
import com.tuibei.service.pay.WxPayService;
import com.tuibei.utils.DateUtils;
import com.tuibei.utils.ResultObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WxPayServiceImpl implements WxPayService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private com.github.binarywang.wxpay.service.WxPayService wxPay;
    @Autowired
    private PrepareOrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    /**
     * 微信统一平台下单
     * 1.查看订单是否是未支付状态
     * 2.查看用户信息，获取openid
     * @param order
     * @return
     * @throws Exception
     */
    @Override
    public ResultObject createPay(Order order) throws Exception{
        Order orderInfo = orderMapper.getOrderInfo(order);
        if(orderInfo==null){
            return ResultObject.build(Constant.ORDERINFO_NULL,Constant.ORDERINFO_NULL_MESSAGE,null);
        }
        if(orderInfo.getStatus()!=0){
            logger.warn("订单：{} 状态错误，无法进行微信平台下单");
            return ResultObject.build(Constant.ORDER_STATUS_ERROR,Constant.ORDER_STATUS_ERROR_MESSAGE,null);

        }
        String member_id = orderInfo.getMember_id();
        User user =new User();
        user.setMember_id(member_id);
        User userInfo = userMapper.getUserInfo(user);
        if(null==userInfo){
            return ResultObject.build(Constant.MEMBER_XXX_NULL,Constant.MEMBER_XXX_NULL_MESSAGE,null);
        }
        user=null;
        String openid =userInfo.getOpenid();
        String order_sn = orderInfo.getOrder_sn();
        logger.info("订单：{} 开始组装微信支付信息",order_sn);

        WxPayUnifiedOrderRequest payOrder =new WxPayUnifiedOrderRequest();
        payOrder.setSpbillCreateIp(order.getClientIp());
        if(order.getPay_type().equals("1")) {
            payOrder.setTradeType(WxPayConstants.TradeType.JSAPI);//小程序公众号支付
        }
        payOrder.setOutTradeNo(order.getOrder_sn());
        payOrder.setOpenid(openid);
        payOrder.setNotifyUrl(Constant.COMMON.DOMAIN+"/pay/notify");
        payOrder.setBody("vip 充值");
        payOrder.setTotalFee(BaseWxPayRequest.yuanToFen(orderInfo.getPrice()));
        Object wxPackage = null;
        try {
            wxPackage = wxPay.createOrder(payOrder);
        } catch (WxPayException e) {
            logger.error("时间：{} ,订单号：{}微信统一下单失败,reason:{}", DateUtils.stableTime(),order_sn,e.getMessage());
            return ResultObject.build(Constant.WX_PAY_EXCEPTION,Constant.WX_PAY_EXCEPTION_MESSAGE,e.getMessage());
        }
        return ResultObject.success(wxPackage);

    }

    @Override
    public void payNotify(WxPayOrderNotifyResult notifyResult) {

        String outTradeNo = notifyResult.getOutTradeNo();
        Integer wxFee = notifyResult.getTotalFee();
        Order order =new Order();
        order.setOrder_sn(outTradeNo);
        Order orderInfo = orderMapper.getOrderInfo(order);
        if(null==orderInfo) return;
        String price = orderInfo.getPrice();
        Integer dbFee = BaseWxPayRequest.yuanToFen(price);
        if(wxFee.equals(dbFee)){
            //更新订单
            order.setStatus(1);
            order.setPay_time(DateUtils.getTimeInSecond_long());


            //更新用户vip 信息
            String member_id = orderInfo.getMember_id();
            User user =new User();
            user.setMember_id(member_id);
            VipModel vipInfo = userMapper.getVipInfo(user);
            long vip_expire_time = Long.valueOf(vipInfo.getVip_expire_time());
            long current_time =DateUtils.getTimeInSecond_long();
            long exp;
            long addTime=0;
            if(orderInfo.getGoods_id().equals("1")){
                user.setLevel_id("1");
                addTime=30*24*60*60;
            }else if(orderInfo.getGoods_id().equals("2")){
                user.setLevel_id("2");
                addTime=365*24*60*60;
            }else{
                user.setLevel_id("0");
            }

            if(vip_expire_time<current_time){
                exp=current_time+addTime;
            }else{
                exp =vip_expire_time+addTime;
            }
            user.setVip_expire_time(String.valueOf(exp));
            userMapper.updateVipInfo(user);
        }

    }
}
