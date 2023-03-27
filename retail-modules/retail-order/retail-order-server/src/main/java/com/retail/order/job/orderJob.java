package com.retail.order.job;

import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.A;
import com.retail.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ProjectName:    retail-cloud
 * @Package:        com.retail.order.job
 * @ClassName:      orderJob
 * @Author:     2766395184
 * @Description:
 * @Date:    2023/3/25 19:51
 * @Version:    1.0
 */
@Component
public class orderJob {

    @Autowired
    private OrderService orderService;






}
