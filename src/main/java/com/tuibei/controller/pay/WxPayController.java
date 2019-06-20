package com.tuibei.controller.pay;

import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.tuibei.model.constant.Constant;
import com.tuibei.model.order.Order;
import com.tuibei.utils.ToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/wx")
public class WxPayController {

@Autowired
private StringRedisTemplate template;

    @GetMapping("/pay")
    public String pay(Order order,HttpServletRequest request){



        String clientIp = ToolsUtils.getClientIp(request);
        order.setClientIp(clientIp);

        Boolean aBoolean = template.opsForValue().setIfAbsent(Constant.COMMON.TBKJSUMSCANORDER, "0");
        System.out.println(aBoolean);
        return  String.valueOf(template.opsForValue().increment(Constant.COMMON.TBKJSUMSCANORDER));
    }






    public static void main(String[] args) throws Exception {
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId("wx1fc44821ba937d67");
        //
        payConfig.setMchId("1540591511");
        // payConfig.setMchId("1520553391");
        payConfig.setMchKey("SXchaojimaijia123456789000000000");
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        //组装微信下单接口数据
        WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
        //openid 用户opened
        orderRequest.setOpenid("o5oef4oE_2nUT5Hid7Rzy2IULbq8");
        orderRequest.setBody("test supersellers");
        orderRequest.setOutTradeNo("1546997230392");
        orderRequest.setNotifyUrl("https://admin.chaojibuyers.com/pay/notify");
        orderRequest.setTotalFee(1234);
        orderRequest.setSpbillCreateIp("114.116.8.180");
        orderRequest.setTradeType("JSAPI");
        Object order = wxPayService.createOrder(orderRequest);

    }

}
