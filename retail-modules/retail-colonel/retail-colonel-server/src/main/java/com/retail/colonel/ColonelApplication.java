package com.retail.colonel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author ColonelApplication
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.colonel
 * @date: 2023-03-23  11:49
 * @Created by:  12871
 * @Description:
 * @Version:
 */
@SpringBootApplication
@EnableFeignClients( basePackages = {"com.retail.colonel"})
@EnableDiscoveryClient
@EnableScheduling
public class ColonelApplication {
    public static void main(String[] args) {
        SpringApplication.run(ColonelApplication.class);
    }

}
