package com.tuibei.controller.pay;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.tuibei.model.constant.Constant;
import com.tuibei.model.order.Order;
import com.tuibei.service.pay.WxPayService;
import com.tuibei.utils.DateUtils;
import com.tuibei.utils.ResultObject;
import com.tuibei.utils.ToolsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/wx")
public class WxPayController {

private Logger logger = LoggerFactory.getLogger(this.getClass());
@Autowired
private WxPayService payService;
@Autowired
private com.github.binarywang.wxpay.service.WxPayService wxPayService;

    @PostMapping("/pay")
    public ResultObject pay(Order order,HttpServletRequest request) throws Exception{

        if(StringUtils.isEmpty(order.getOrder_sn())){
            return ResultObject.build(Constant.ORDER_NULL,Constant.ORDER_NULL_MESSAGE,null);
        }
        if(StringUtils.isEmpty(order.getPay_type())){
            return ResultObject.build(Constant.WX_PAY_TYPE_NULL,Constant.WX_PAY_TYPE_NULL_MESSAGE,null);
        }
        String clientIp = ToolsUtils.getClientIp(request);
        order.setClientIp(clientIp);
        try {
            ResultObject result = payService.createPay(order);
            return result;
        }catch (Exception e){
            logger.error("微信支付异常：{}",e.getMessage());
            return ResultObject.error(null);
        }


    }

    @PostMapping("/notify")
    public String notify(@RequestBody String xmlData) {

        logger.info("时间：{}接收到微信支付回调通知", DateUtils.stableTime());
        WxPayOrderNotifyResult notifyResult =null;
        try {
            notifyResult= wxPayService.parseOrderNotifyResult(xmlData);
        }catch (WxPayException py){
            logger.error("接收微信回调错误：{}",py.getMessage());
            return returnXML("FAIL");
        }

        logger.info("开始处理微信回调,订单号:{} ",notifyResult.getOutTradeNo());

        return returnXML(notifyResult.getResultCode());
    }

    private String returnXML(String return_code) {

        return "<xml><return_code><![CDATA["

                + return_code

                + "]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }






    public static void main(String[] args) throws Exception {
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId("wx01c2d4e39bcb87a7");
        //
        payConfig.setMchId("1540591511");
        // payConfig.setMchId("1520553391");
        payConfig.setMchKey("34d9c8e28de2fd8d5e028447b1c86d32");
        com.github.binarywang.wxpay.service.WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        //组装微信下单接口数据
        WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
        //openid 用户opened
        orderRequest.setOpenid("ojx9a5KcU8T2Xr6fwoJVQ4mHoP94");
        orderRequest.setBody("test supersellers");
        orderRequest.setOutTradeNo("1546997230392");
        orderRequest.setNotifyUrl("https://admin.chaojibuyers.com/pay/notify");
        orderRequest.setTotalFee(1234);
        orderRequest.setSpbillCreateIp("114.116.8.180");
        orderRequest.setTradeType("JSAPI");
        Object order = wxPayService.createOrder(orderRequest);

    }

}
