package com.retail.colonel.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName TestConfig
 * Date 2023/4/1 10:04
 **/
@Configuration
public class TestConfig {
    @Value("${spring.datasource.url}")
    String ss;
    @Value("${sss}")
    String sss;
    @Bean
    public void ss(){
        System.out.println(ss);
        System.out.println(sss);
    }
}
