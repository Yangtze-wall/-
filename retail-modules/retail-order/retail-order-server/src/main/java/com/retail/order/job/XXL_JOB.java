package com.retail.order.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * ClassName XXL_JOB
 * Date 2023/4/13 19:47
 **/
@Component
public class XXL_JOB {
    @Scheduled(cron = "0/2 * * * * *")
    public void ss(){
        System.out.println("ss");
    }

}
