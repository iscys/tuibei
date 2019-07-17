package com.tuibei.service.pay.impl;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.tuibei.mapper.earning.EarningMapper;
import com.tuibei.mapper.rule.RuleMapper;
import com.tuibei.mapper.user.UserMapper;
import com.tuibei.model.earning.Earning;
import com.tuibei.model.earning.Income;
import com.tuibei.model.order.Order;
import com.tuibei.model.rule.Rule;
import com.tuibei.model.user.User;
import com.tuibei.model.user.VipModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@Service
@Transactional
public class TuiGuangRule {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RuleMapper ruleMapper;
    @Autowired
    private EarningMapper earningMapper;

    /**
     * 推广分销
     * @param notifyResult
     * @param orderInfo
     * @param vipInfo
     * @param rule
     * @param times 分销级数
     */
    public void tuiguang(WxPayOrderNotifyResult notifyResult, Order orderInfo,
                         VipModel vipInfo, Rule rule, int times) {

        if(null ==rule){
            Rule db_rule = ruleMapper.getAllRule();
            rule=db_rule;
        }
        String goods_id = orderInfo.getGoods_id();//商品ID
        if(goods_id.equals("0"))return;
        String member_id=orderInfo.getMember_id();
        String order_sn =orderInfo.getOrder_sn();
        String price = orderInfo.getPrice();//价钱


        User user =new User();
        user.setMember_id(vipInfo.getMember_id());


            User userInfo = userMapper.getUserInfo(user);
            //邀请人
            String master = userInfo.getMaster();
            if (!StringUtils.isEmpty(master)) {
                String discount =null;

                //获取分销折扣
                if(times==1){
                    discount=rule.getFirst_discount();
                }else{
                    discount =rule.getSecond_discount();
                }
                logger.info("获取分利润比例：{}",discount+"%");
                BigDecimal deci_discount=new BigDecimal(Double.valueOf(discount)/100);
                BigDecimal deci_price=new BigDecimal(price);
                BigDecimal multiply = deci_price.multiply(deci_discount);
                //给予到的分成利润
                double earnings = multiply.doubleValue();
                logger.info("用户：{} 获取利润：{}",master,earnings);
                Earning ng =new Earning();
                ng.setEarning(earnings);
                ng.setEarning_member_id(master);
                ng.setOrder_sn(order_sn);
                ng.setOrder_member_id(member_id);
                ng.setGoods_id(goods_id);
                //保存用户收益支出信息
                logger.info("保存用户：{}收益支出信息",master);
                Income income =new Income();
                income.setMember_id(master);
                income.setOrder_sn(order_sn);
                income.setPrice(earnings);
                income.setOrder_member_id(member_id);
                income.setType(0);
                earningMapper.saveIncomeInfo(income);
                logger.info("保存收益支出成功");

                //更新用户账户
                User upVipAccount =new User();
                upVipAccount.setMember_id(master);
                upVipAccount.setAccount(earnings);
                userMapper.updateVipInfo(upVipAccount);


                //保存分成记录日志
                earningMapper.saveEarningLog(ng);
                logger.info("保存利润成功");


                if(times<rule.getTotal_times()) {
                    VipModel vip =new VipModel();
                    vip.setMember_id(master);
                    //找上一级递归
                    tuiguang(notifyResult, orderInfo, vip,rule, ++times);
                }
            }



    }








}
