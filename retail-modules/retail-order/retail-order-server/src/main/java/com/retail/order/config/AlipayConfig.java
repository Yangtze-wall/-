package com.retail.order.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName AlipayConfig
 * Date 2023/3/30 11:47
 **/
@Configuration
public class AlipayConfig {
    //定义初始的请求
    @Value("${alipay.appid}")
    private String appId;
    @Value("${alipay.url}")
    private String url;
    @Value("${alipay.privateKey}")
    private String privateKey;
    @Value("${alipay.publicKey}")
    private String publicKey;
    @Bean
    public AlipayClient alipayClient(){
        return new DefaultAlipayClient(url, appId, privateKey, "json", "UTF-8", publicKey, "RSA2");
    }
}
