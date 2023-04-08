package com.retail.user;

import cn.hutool.core.util.IdUtil;
import com.retail.common.utils.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author UserApplication
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.user
 * @date: 2023-03-22  18:35
 * @Created by:  12871
 * @Description:
 * @Version:
 */
@SpringBootApplication
@EnableFeignClients( basePackages = {"com.retail.user"})
@EnableDiscoveryClient
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class);
    }

}
