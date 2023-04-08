package com.retail.order;

import cn.hutool.core.util.IdUtil;
import com.retail.common.utils.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Date;

/**
 * @author OrderApplication
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.order.retail.order
 * @date: 2023-03-22  19:01
 * @Created by:  12871
 * @Description:
 * @Version:
 */
@SpringBootApplication
@EnableFeignClients( basePackages = {"com.retail.order"})
@EnableDiscoveryClient
public class OrderApplication {
    public static void main(String[] args) {
       SpringApplication.run(OrderApplication.class);
//        DateTime dateTime = new DateTime(new Date());
//        int day = dateTime.dayOfWeek();
//        System.out.println(day);
//        Long user= 850497767598981120L;
//        String substring = IdUtil.getSnowflake(1, 1).nextIdStr().substring(6);
//        String orderSn = substring+ StringUtils.leftPad(String.valueOf(1),8,"0");
//        System.out.println(orderSn);

    }

}
