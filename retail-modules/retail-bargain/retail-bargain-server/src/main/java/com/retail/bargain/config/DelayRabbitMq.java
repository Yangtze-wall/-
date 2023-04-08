package com.retail.bargain.config;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.retail.bargain.domain.BargainEntity;
import com.retail.bargain.mapper.BargainMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


/**
 * @author Lenovo
 * 延迟队列
 */
@Configuration
@Log4j2
public class DelayRabbitMq {
    @Autowired
    private BargainMapper bargainMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    /**
     * 业务队列
     */
    public static final String QUEUE_NAME = "bargain_queue";
    /**
     * 死信队列
     */
    public static final String DEAD_QUEUE = "bargain_dead_queue";
    /**
     * 死信交换机
     */
    public static final String DEAD_EXCHANGE = "bargain_exchange";
    /**
     * 死信路由
     */
    public static final String DEAD_QUEUE_KEY = "bargain_key";


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


    @RabbitListener(queues = DEAD_QUEUE)
    public void  queueDead(String json,Channel channel,Message message){
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        String messageId = message.getMessageProperties().getMessageId();
        Long add = redisTemplate.opsForSet().add(messageId);
        if (add>=1){
            try {
                List<BargainEntity> bargainEntityList = JSONObject.parseArray(json, BargainEntity.class);
                bargainEntityList.stream().forEach(c -> {
                    long l = System.currentTimeMillis();
                    if (l>c.getTotalExpirationTime().getTime()){
                        BargainEntity bargainEntity = new BargainEntity();
                        bargainEntity.setId(c.getId());
                        bargainEntity.setStatus(1);
                        bargainEntity.setBargainStatus(1);
                        bargainMapper.updateById(bargainEntity);
                    }
                });
                channel.basicAck(deliveryTag,true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }





}
