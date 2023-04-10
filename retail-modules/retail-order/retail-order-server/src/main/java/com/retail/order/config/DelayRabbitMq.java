package com.retail.order.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;


/**
 * @author Lenovo
 * 延迟队列
 */
//@Configuration
@Log4j2
public class DelayRabbitMq {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    /**
     * 业务队列
     */
    public static final String QUEUE_NAME = "";
    /**
     * 死信队列
     */
    public static final String DEAD_QUEUE = "";
    /**
     *
     * 死信交换机
     */
    public static final String DEAD_EXCHANGE = "";
    /**
     * 死信路由
     */
    public static final String DEAD_QUEUE_KEY = "";


    /**
     * 实例化业务队列
     */
    @Bean
    public Queue createQueue(){
        HashMap<String, Object> map = new HashMap<>();
        //绑定死信交换机
        map.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        //路由
        map.put("x-dead-letter-routing-key", DEAD_QUEUE_KEY);
        //设置过期时间
        map.put("x-message-ttl", 60000);
        return QueueBuilder.durable(QUEUE_NAME).withArguments(map).build();
    }
    /**
     * 实例化死信队列
     */
    @Bean
    public Queue createDead(){
        Queue queue = new Queue(DEAD_QUEUE,true);

        return queue;
    }

    /**
     * 实例化死信交换机
     */
    @Bean
    public Exchange createExchange(){
        DirectExchange directExchange = new DirectExchange(DEAD_EXCHANGE);
        return directExchange;
    }


    /**
     * 死信队列绑定死信交换机
     */

    @Bean
    public Binding createBinding(){
        Binding binding = new Binding(DEAD_QUEUE, Binding.DestinationType.QUEUE, DEAD_EXCHANGE, DEAD_QUEUE_KEY, null);
        return binding;
    }






}
