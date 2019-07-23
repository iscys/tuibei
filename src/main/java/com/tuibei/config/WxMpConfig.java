package com.tuibei.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxMpConfig {

    @Bean
    public WxMpService wxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
        config.setAppId("wx426aad126775582c");
        config.setSecret("d79b69215c50cee0c848415eb34c659a");
        wxMpService.setWxMpConfigStorage(config);
        return wxMpService;
    }

    @Bean
    public ObjectMapper objectMapper() {

        return new ObjectMapper();
    }
}
