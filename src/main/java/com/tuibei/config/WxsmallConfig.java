package com.tuibei.config;

import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

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
/*
    public static void main(String[] args)throws Exception {

        WxMaInMemoryConfig miniConfig=new WxMaInMemoryConfig();
        miniConfig.setAppid("wx01c2d4e39bcb87a7");
        miniConfig.setSecret("96168bb842888f41ae6a89967c3df24b");
        WxMaServiceImpl maService =new WxMaServiceImpl();
        maService.setWxMaConfig(miniConfig);
        File qrcode = maService.getQrcodeService().createWxaCode("pages/login/login");
        System.out.println(qrcode.toURI());
        FileInputStream in =new FileInputStream(qrcode);
        FileOutputStream out =new FileOutputStream("/Users/iscys/Desktop/qrcode.jpg");
        IOUtils.copy(in,out);

    }
    **/

}
