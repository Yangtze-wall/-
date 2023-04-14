package com.retail.order.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.retail.common.domain.vo.InventoryEntityVo;
import com.retail.order.domain.OrderEntity;
import com.retail.order.feign.ShopFeignService;
import com.retail.order.mapper.OrderMapper;
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
public class OrderRabbitMq {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private ShopFeignService shopFeignService;

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 业务队列
     */
    public static final String QUEUE_NAME = "order_queue";
    /**
     * 死信队列
     */
    public static final String DEAD_QUEUE = "order_dead_queue";
    /**
     *
     * 死信交换机
     */
    public static final String DEAD_EXCHANGE = "order_exchange";
    /**
     * 死信路由
     */
    public static final String DEAD_QUEUE_KEY = "order_key";


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


    // 订单5分钟没有支付，自动取消，使用rabbitMQ延迟队列)
    // 回滚库存
    @RabbitListener(queues = DEAD_QUEUE)
    public void  queueDead(String json, Channel channel, Message message){

        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        String messageId = message.getMessageProperties().getMessageId();
        Long add = redisTemplate.opsForSet().add(messageId);
        try {
            if (add>=0){
                List<OrderEntity> orderEntityList = JSON.parseArray(json, OrderEntity.class);
               // 修改订单order状态  3.支付失败
                orderEntityList.stream().forEach(c-> {
                    c.setStatus(3);
                    orderMapper.updateById(c);
                    Long spuId = c.getSpuId();
                    // 库存表  锁库存
                    InventoryEntityVo entityVo = shopFeignService.findInventoryBySpuId(spuId).getData();
                    InventoryEntityVo inventoryEntityVo = new InventoryEntityVo();
                    inventoryEntityVo.setSpuId(spuId);
                    inventoryEntityVo.setInventoryLock(entityVo.getInventoryLock()-1);
                    shopFeignService.updateInventory(inventoryEntityVo);
                });
            }
            channel.basicAck(deliveryTag,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
