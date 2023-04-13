//package com.retail.colonel.config;
//
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
///**
// * @author Lenovo
// * redission
// */
//@Configuration
//public class RedisSonConfig {
//    @Bean
//    public RedissonClient redissonClient(){
//        Config config = new Config();
//        config.useSingleServer().setAddress("redis://192.168.127.133:6379");
//        RedissonClient redissonClient = Redisson.create(config);
//        return redissonClient;
//    }
//
//
//}
