package com.tuibei.service.order.impl;

import com.tuibei.mapper.order.PrepareOrderMapper;
import com.tuibei.model.order.Order;
import com.tuibei.model.order.OrderVO;
import com.tuibei.service.order.PrepareOrderService;
import com.tuibei.utils.DateUtils;
import com.tuibei.utils.ResultObject;
import com.tuibei.utils.ToolsUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataUnit;

@Service
public class PrepareOrderServiceImpl implements PrepareOrderService {

    @Autowired
    private PrepareOrderMapper orderMapper;
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
        order.setGoods_name("vip 充值");
        orderMapper.saveOrder(order);
        OrderVO retOrder =new OrderVO();
        BeanUtils.copyProperties(order,retOrder);
        return ResultObject.success(retOrder);
    }
}
