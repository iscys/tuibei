package com.tuibei.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = WxMpProperties.WXMP_PREFIX)
@Data
public class WxMpProperties {
    /**
     *
     */
    public static final String WXMP_PREFIX = "wxmp";


    private String appid;
    private String appSecret;
    private String mchId;
    private String mchKey;

}
