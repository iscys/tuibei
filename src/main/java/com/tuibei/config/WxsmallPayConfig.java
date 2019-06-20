package com.tuibei.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxsmallPayConfig {
    @Value("${wxSmall.appid}")
    private String appid;
    @Value("${wxSmall.appSecret}")
    private String appSecret;
    @Value("${wxSmall.mchId}")
    private String mchId;
    @Value("${wxSmall.mchKey}")
    private String mchKey;

    /**
     * 微信小程序服务支付
     */
    @Bean
    public WxPayService wxpay(){
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(appid);
        payConfig.setMchId(mchId);
        payConfig.setMchKey(mchKey);
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        return wxPayService;
    }


}
