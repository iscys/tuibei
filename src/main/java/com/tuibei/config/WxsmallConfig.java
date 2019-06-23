package com.tuibei.config;

import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(WxsmallProperties.class)
public class WxsmallConfig {

    @Autowired
    private WxsmallProperties wxsmallProperties;
    /**
     * 微信小程序服务
     */
    @Bean
    public WxMaServiceImpl miniProgram(){

        WxMaInMemoryConfig miniConfig=new WxMaInMemoryConfig();
        miniConfig.setAppid(wxsmallProperties.getAppid());
        miniConfig.setSecret(wxsmallProperties.getAppSecret());
        WxMaServiceImpl maService =new WxMaServiceImpl();
        maService.setWxMaConfig(miniConfig);
        return maService;
    }

}
