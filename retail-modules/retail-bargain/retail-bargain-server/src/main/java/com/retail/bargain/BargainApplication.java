package com.retail.bargain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author BargainApplication
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.bargain
 * @date: 2023-03-23  11:55
 * @Created by:  12871
 * @Description:
 * @Version:
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class BargainApplication {
    public static void main(String[] args) {
        SpringApplication.run(BargainApplication.class);
    }
}
