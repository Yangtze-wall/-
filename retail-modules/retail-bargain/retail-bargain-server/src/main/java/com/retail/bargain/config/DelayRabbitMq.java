package com.retail.bargain.config;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rabbitmq.client.Channel;
import com.retail.bargain.domain.SeckillEntity;
import com.retail.bargain.domain.request.StartSeckill;
import com.retail.bargain.mapper.SeckillMapper;
import com.retail.bargain.vo.OrderVo;
import com.retail.bargain.vo.SkuEntity;
import com.retail.common.domain.vo.InventoryVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
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
    private RedisTemplate<String,String> redisTemplate;
    @Resource
    private SeckillMapper seckillMapper;
    /**
     * 业务队列
     */
    public static final String QUEUE_NAME = "seckill_queue";
    /**
     * 死信队列
     */
    public static final String DEAD_QUEUE = "seckill_dead_queue";
    /**
     *
     * 死信交换机
     */
    public static final String DEAD_EXCHANGE = "seckill_dead_exchange";
    /**
     * 死信路由
     */
    public static final String DEAD_QUEUE_KEY = "seckill_dead_queue_key";
    /**
     *
     * 业务队列交换机
     */
    public static final String QUEUE_EXCHANGE = "seckill_queue_exchange";
    /**
     * 业务队列路由
     */
    public static final String QUEUE_QUEUE_KEY = "seckill_queue_queue_key";


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
        //设置过期时间  15分钟
        map.put("x-message-ttl", 900000);
//        map.put("x-message-ttl", 300);
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
    public Exchange createDeadExchange(){
        DirectExchange directExchange = new DirectExchange(DEAD_EXCHANGE);
        return directExchange;
    }


    /**
     * 死信队列绑定死信交换机
     */
    @Bean
    public Binding createDeadBinding(){
        Binding binding = new Binding(DEAD_QUEUE, Binding.DestinationType.QUEUE, DEAD_EXCHANGE, DEAD_QUEUE_KEY, null);
        return binding;
    }

    /**
     * 实例化业务交换机
     */
    @Bean
    public Exchange createExchange(){
        DirectExchange directExchange = new DirectExchange(QUEUE_EXCHANGE);
        return directExchange;
    }


    /**
     * 业务队列绑定业务交换机
     */

    @Bean
    public Binding createBinding(){
        Binding binding = new Binding(QUEUE_NAME, Binding.DestinationType.QUEUE, QUEUE_EXCHANGE, QUEUE_QUEUE_KEY, null);
        return binding;
    }

    @RabbitListener(queues = DEAD_QUEUE)
    public void seckillMq(String sms, Channel channel,Message message){
        StartSeckill startSeckill = JSONObject.parseObject(sms, StartSeckill.class);
        //查询是否支付
        OrderVo orderVo = seckillMapper.findByIdOrder(startSeckill.getOrderId());
        //秒杀商品明细
        List<SeckillEntity> list = seckillMapper.selectList(new QueryWrapper<SeckillEntity>().lambda().eq(SeckillEntity::getId, startSeckill.getSeckillId()));
        SeckillEntity seckillList = list.get(0);
        //sku
        SkuEntity skuEntity = seckillMapper.findByIdSku(seckillList.getSkuId());
        //查询库存表
        InventoryVo inventory = seckillMapper.findByIdInventory(skuEntity.getSpuId());
        try {
            if(orderVo.getStatus()!=2){
                //未支付,回滚库存
                SeckillEntity seckillEntity = new SeckillEntity();
                seckillEntity.setId(startSeckill.getSeckillId());
                seckillEntity.setSeckillResidueCount(seckillList.getSeckillResidueCount()+startSeckill.getCount());
                seckillMapper.updateById(seckillEntity);
                //回滚库存表锁的库存 锁库存数量-购买数量
                seckillMapper.updateInventoryLock(inventory.getId(),inventory.getInventoryLock()-startSeckill.getCount());
//                //回滚库存表的库存 库存数量+购买数量
//                seckillMapper.updateInventoryCount(inventory.getId(),inventory.getInventoryCount()+startSeckill.getCount());
                //修改订单状态
                seckillMapper.updateOrderStatus(startSeckill.getOrderId());
                log.info("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            }
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),true);
        }catch (Exception e){
            e.printStackTrace();
        }

    }





}
