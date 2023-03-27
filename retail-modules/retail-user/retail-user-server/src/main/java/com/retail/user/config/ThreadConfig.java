package com.retail.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ThreadConfig
 * @BelongsProject: yk3.17
 * @BelongsPackage: com.bawei.config
 * @date: 2023-03-17  09:01
 * @Created by:  12871
 * @Description:
 * @Version:
 */
@Configuration

public class ThreadConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(24, 54, 3, TimeUnit.HOURS,
                new LinkedBlockingQueue<>(3));
        return threadPoolExecutor;
    }

}
