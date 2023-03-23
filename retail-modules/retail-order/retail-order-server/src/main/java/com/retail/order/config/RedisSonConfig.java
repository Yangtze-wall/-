package com.retail.order.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author Lenovo
 * redission
 */
@Configuration
public class RedisSonConfig {
    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://139.196.95.132:6379");
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }


}
