package com.tuibei.controller.pay;

import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WxPayController {


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
