package com.tuibei.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(WxMpProperties.class)
public class WxMpConfig {

    @Autowired
    private WxMpProperties properties;

    @Bean
    public WxMpService wxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
        config.setAppId(properties.getAppid());
        config.setSecret(properties.getAppSecret());
        wxMpService.setWxMpConfigStorage(config);
        return wxMpService;
    }

}
