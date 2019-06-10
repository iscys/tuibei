package com.tuibei.config;

import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxsmallConfig {
    @Value("${wxSmall.appid}")
    private String appid;
    @Value("${wxSmall.appSecret}")
    private String appSecret;
    /**
     * 微信小程序服务
     */
    @Bean
    public WxMaServiceImpl miniProgram(){

        WxMaInMemoryConfig miniConfig=new WxMaInMemoryConfig();
        miniConfig.setAppid(appid);
        miniConfig.setSecret(appSecret);
        WxMaServiceImpl maService =new WxMaServiceImpl();
        maService.setWxMaConfig(miniConfig);
        return maService;
    }

}
