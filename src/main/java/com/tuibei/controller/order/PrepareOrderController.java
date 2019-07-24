package com.tuibei.controller.order;

import com.tuibei.model.constant.Constant;
import com.tuibei.model.order.Order;
import com.tuibei.model.user.User;
import com.tuibei.service.order.PrepareOrderService;
import com.tuibei.utils.ResultObject;
import com.tuibei.utils.ToolsUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 生成订单
 */
@RestController
@RequestMapping("/prepare")
public class PrepareOrderController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PrepareOrderService delegateService;

    @PostMapping("/order")
    public ResultObject prepareOrder(Order order){
        int origin = order.getOrigin();
        String goods_id = order.getGoods_id();
        String price = order.getPrice();
        String member_id = order.getMember_id();

        if(StringUtils.isEmpty(price)){
            return ResultObject.build(Constant.GOODS_PRICE_NULL,Constant.GOODS_PRICE_NULL_MESSAGE,null);
        }
        if(StringUtils.isEmpty(goods_id)){
            return ResultObject.build(Constant.GOODS_ID_NULL,Constant.GOODS_ID_NULL_MESSAGE,null);
        }

        if(origin==2){//if 订单来自于公众号，需要code以及手机号码校验
            String phone = order.getPhone();
            String wxCode =order.getCode();
            if(StringUtils.isEmpty(phone)&& !ToolsUtils.checkMobileNumber(phone)){
                return ResultObject.build(Constant.PHONE_ERROR,Constant.PHONE_ERROR_MESSAGE,null);
            }
            if(StringUtils.isEmpty(wxCode)){
                return ResultObject.build(Constant.WX_CODE_NULL,Constant.WX_CODE_NULL_MESSAGE,null);
            }
        }
        if(origin==1) {
            if (StringUtils.isEmpty(member_id)) {
                return ResultObject.build(Constant.MEMBER_XXX_NULL, Constant.MEMBER_XXX_NULL_MESSAGE, null);
            }
            logger.info("来源：{}用户：{},商品id:{} 价钱：{} 开始创建订单",origin,member_id,goods_id,price);

        }


    try {

        ResultObject result = delegateService.createOrder(order);
        return result;
    }catch (Exception e){
        return ResultObject.error(null);
    }

    }

    /**
     * 新用户免费天数使用
     * @param user
     * @return
     */

    @PostMapping("/free")
    @Deprecated
    public ResultObject prepareOrder(User user){
        String member_id = user.getMember_id();
        if(StringUtils.isEmpty(member_id)){
            return ResultObject.build(Constant.MEMBER_XXX_NULL,Constant.MEMBER_XXX_NULL_MESSAGE,null);
        }


        try {

            ResultObject result = delegateService.freeDay(user);
            return result;
        }catch (Exception e){
            logger.error("用户：{}使用免费次数异常：{}",member_id,e.getMessage());
            return ResultObject.error(null);
        }

    }
}
