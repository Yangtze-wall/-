package com.retail.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * ClassName PayMentApplication
 * Date 2023/4/11 15:29
 **/
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class PayMentApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayMentApplication.class);
    }
}
