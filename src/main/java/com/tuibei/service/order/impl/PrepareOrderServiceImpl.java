package com.tuibei.service.order.impl;

import com.tuibei.mapper.order.PrepareOrderMapper;
import com.tuibei.mapper.user.UserMapper;
import com.tuibei.model.constant.Constant;
import com.tuibei.model.order.Order;
import com.tuibei.model.order.OrderVO;
import com.tuibei.model.user.User;
import com.tuibei.model.user.VipModel;
import com.tuibei.service.order.OrderPay;
import com.tuibei.service.order.PrepareOrderService;
import com.tuibei.utils.DateUtils;
import com.tuibei.utils.ResultObject;
import com.tuibei.utils.ToolsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;

@Service
public class PrepareOrderServiceImpl implements PrepareOrderService, InitializingBean {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PrepareOrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GzhOrderPay gzhOrderPay;
    @Autowired
    private WxsmallOrderPay WxsmallOrderPay;

    private HashMap<Integer, OrderPay> orderPayHashMap;
    /**
     * 创建订单
     * @param order
     * @return
     * @throws Exception
     */
    @Override
    public ResultObject createOrder(Order order) throws Exception {
/**
        if(order.getGoods_id().equals("0")){
            User user =new User();
            user.setMember_id(order.getMember_id());

            VipModel vipInfo = userMapper.getVipInfo(user);
            return freeDay(user);

        }
**/
        int origin = order.getOrigin();
        OrderPay orderPay = orderPayHashMap.get(origin);

        try {
            orderPay.createOrder(order);
            if(StringUtils.isEmpty(order.getOpenid())){
                logger.error("获取用户参数信息为空：来源:{} 用户 :member_id={},手机号：{},openid:{}",
                        order.getOrigin(),order.getMember_id(),order.getPhone(),order.getOpenid());
                return ResultObject.build(Constant.MEMBER_XXX_NULL,Constant.MEMBER_XXX_NULL_MESSAGE,null);
            }


        }catch (Exception e){
            logger.error("订单生成错误,错误信息：{} 来源 -> {}",e.getMessage(),origin);
            ResultObject.build(Constant.ORDER_ERROR,Constant.ORDER_ERROR_MESSAGE,null);
        }
        if(order.getGoods_id().equals("1")) {
            order.setGoods_name("季卡充值");
        }
        else if(order.getGoods_id().equals("2")){
            order.setGoods_name("年卡充值");
        }else if(order.getGoods_id().equals("3")){
            order.setGoods_name("月卡充值");

        }else if(order.getGoods_id().equals("4")){
            order.setGoods_name("半年卡充值");
        }

        orderMapper.saveOrder(order);
        OrderVO retOrder =new OrderVO();
        BeanUtils.copyProperties(order,retOrder);
        return ResultObject.success(retOrder);
    }


    /**
     * 1.user_vip 查看是否使用了免费天数
     * 2.添加
     *
     * @param user
     * @return
     */
    @Override
    public ResultObject freeDay(User user) {
        VipModel vipInfo = userMapper.getVipInfo(user);
        if(null==vipInfo){
            return ResultObject.build(Constant.MEMBER_XXX_NULL,Constant.MEMBER_XXX_NULL_MESSAGE,null);

        }

        if(vipInfo.getUse_free()==1){
            logger.warn("用户：{} 已经使用过了免费优惠",user.getMember_id());
            return ResultObject.build(Constant.HAS_USE_FREE_DAY,Constant.HAS_USE_FREE_DAY_MESSAGE,null);
        }
        long exp;
        long vip_expire_time = vipInfo.getVip_expire_time_long();
        long current_time =DateUtils.getTimeInSecond_long();
        user.setUse_free(1);
        int freeDay=Constant.COMMON.FREEDAY;
        if(vip_expire_time<current_time) {
            exp = current_time + freeDay * 24 * 60 * 60;
        }else{
            exp=vip_expire_time +freeDay * 24 * 60 * 60;
        }
        user.setVip_expire_time(String.valueOf(exp));
        user.setLevel_id("3");
        userMapper.updateVipInfo(user);
        logger.info("用户：{} 获取了免费使用天数，截止日期：{}",user.getMember_id(),DateUtils.secondamp2date(exp));
        vipInfo=null;
        return ResultObject.success(null);
    }

    @Override
    public HashMap<Integer, OrderPay> getOrderPayCache() {

        return orderPayHashMap;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        orderPayHashMap =new HashMap<Integer, OrderPay>();
        orderPayHashMap.put(1,WxsmallOrderPay);//小程序支付
        orderPayHashMap.put(2,gzhOrderPay);//公众号支付
    }
}
