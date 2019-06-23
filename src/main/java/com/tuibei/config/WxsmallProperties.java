package com.tuibei.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = WxsmallProperties.WXSMALL_PREFIX)
@Data
public class WxsmallProperties {
    /**
     *
     */
    public static final String WXSMALL_PREFIX = "wxsmall";


    private String appid;
    private String appSecret;
    private String mchId;
    private String mchKey;



}
