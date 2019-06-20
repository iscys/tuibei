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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.util.unit.DataUnit;

@Service
public class PrepareOrderServiceImpl implements PrepareOrderService {

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
        //测试数据
        if(order.getGoods_id().equals("1"))
            order.setPrice("0.01");
        else if(order.getGoods_id().equals("2"))
            order.setPrice("0.02");
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
            return ResultObject.build(Constant.HAS_USE_FREE_DAY,Constant.HAS_USE_FREE_DAY_MESSAGE,null);
        }
        user.setUse_free(1);
        long exp=DateUtils.getTimeInSecond_long()+7*24*60*60;
        user.setVip_expire_time(String.valueOf(exp));
        userMapper.updateVipInfo(user);
        return ResultObject.success(null);
    }


}
