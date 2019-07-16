package com.tuibei.service.pay.impl;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.tuibei.mapper.user.UserMapper;
import com.tuibei.model.order.Order;
import com.tuibei.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class TuiGuangRule {
    @Autowired
    private UserMapper userMapper;

    /**
     * 推广利润分成
     * @param notifyResult
     */
    public void tuiguang(WxPayOrderNotifyResult notifyResult, Order orderInfo) {
        String goods_id = orderInfo.getGoods_id();//商品ID
        if(goods_id.equals("0"))return;
        String member_id=orderInfo.getMember_id();
        String order_sn =orderInfo.getOrder_sn();
        String price = orderInfo.getPrice();//价钱
        User user =new User();
        user.setMember_id(member_id);
        User userInfo = userMapper.getUserInfo(user);

        //查看他的邀请人
        String master = userInfo.getMaster();
        if(!StringUtils.isEmpty(master)){
            //如果有master的话，就查找mater 邀请了多少人
        }

    }



}
