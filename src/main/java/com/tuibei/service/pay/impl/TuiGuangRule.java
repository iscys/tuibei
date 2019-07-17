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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

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
            User masterUser =new User();
            masterUser.setMember_id(master);
            User masterUserInfo = userMapper.getUserInfo(masterUser);
            Rule rule = rule(masterUserInfo);
            logger.info("获取到匹配到规则：{}",rule.toString());
            String discount = rule.getDiscount();
            if(!StringUtils.isEmpty(discount)){
                double dis = Double.valueOf(discount);
                BigDecimal deci_discount=new BigDecimal(dis/100);
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






            }

        }

    }


    /**
     * 返回匹配的规则
     * @param user
     * @return
     */
    public Rule rule(User user){
        logger.info("获取用户推广利润规则:{}",user.toString());

        Rule mainRule =new Rule();

        int invite_count = user.getInvit_count();
        Rule rule =new Rule();
       List<Rule> allRule= ruleMapper.getAllRule(rule);
       for(Rule r:allRule){
           int max_count = r.getMax_count();
           int min_count =r.getMin_count();
           if(invite_count>min_count&&invite_count<=max_count){
                mainRule =r;
                break;
           }

       }
       

        return mainRule;
    }




}
