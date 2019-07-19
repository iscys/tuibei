package com.tuibei.service.order.impl;

import com.tuibei.mapper.order.PrepareOrderMapper;
import com.tuibei.mapper.user.UserMapper;
import com.tuibei.model.constant.Constant;
import com.tuibei.model.order.Order;
import com.tuibei.model.order.OrderVO;
import com.tuibei.model.user.User;
import com.tuibei.model.user.VipModel;
import com.tuibei.service.order.PrepareOrderService;
import com.tuibei.utils.DateUtils;
import com.tuibei.utils.ResultObject;
import com.tuibei.utils.ToolsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrepareOrderServiceImpl implements PrepareOrderService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PrepareOrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    /**
     * 创建订单
     * @param order
     * @return
     * @throws Exception
     */
    @Override
    public ResultObject createOrder(Order order) throws Exception {

        //生成订单号
        String order_sn=DateUtils.getTimeInMillis()+ ToolsUtils.sixCode();
        Long time =DateUtils.getTimeInSecond_long();
        order.setOrder_sn(order_sn);
        order.setTime(time);
        order.setOrigin(1);//来自小程序的订单
        order.setPay_method(1);
        order.setGoods_name("vip 充值");

        User user =new User();
        user.setMember_id(order.getMember_id());
        if(order.getGoods_id().equals("0")){

            VipModel vipInfo = userMapper.getVipInfo(user);
            if(vipInfo.getUse_free()==1){
                logger.warn("用户：{} 已经使用过了免费体验",user.getMember_id());
                return ResultObject.build(Constant.HAS_USE_PRIVILEGE,Constant.HAS_USE_PRIVILEGE_MESSAGE,null);
            }

        }


        //测试数据
        if(order.getGoods_id().equals("0")){
            order.setGoods_name("月卡充值");

        }else if(order.getGoods_id().equals("1")) {
            order.setGoods_name("月卡充值");
        }
        else if(order.getGoods_id().equals("2")){
            order.setGoods_name("年卡充值");
        }else{
            order.setPrice("999");
            order.setGoods_name("Vip充值");
        }
        //测试end

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
            logger.warn("用户：{} 已经使用过了免费天数",user.getMember_id());
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


}
