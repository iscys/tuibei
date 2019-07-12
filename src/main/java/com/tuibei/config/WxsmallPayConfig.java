package com.tuibei.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(WxsmallProperties.class)
public class WxsmallPayConfig {

    @Autowired
    private WxsmallProperties properties;
    /**
     * 微信小程序服务支付
     */
    @Bean
    public WxPayService wxpay(){
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(properties.getAppid());
        payConfig.setMchId(properties.getMchId());
        payConfig.setMchKey(properties.getMchKey());
        payConfig.setKeyPath("classpath:apiclient_cert.p12");
        //payConfig.setKeyPath();
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        return wxPayService;
    }


}
