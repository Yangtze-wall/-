package com.retail.bargain.controller;

import java.util.List;


import com.retail.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.retail.bargain.domain.SeckillEntity;
import com.retail.bargain.service.SeckillService;


/**
 * 秒杀商品表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 11:22:38
 */
@RestController
@RequestMapping("bargain/seckill")
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    @PostMapping("/getSeckillList")
    public Result<List<SeckillEntity>> getSeckillList(){
        return seckillService.select();
    }

    //消费者端-秒杀活动-支付(支付成功后给用户加积分，调用用户加积分接口)

    // 消费者端-秒杀活动-支付(分布式事务，seata)

    // 消费者端-秒杀活动-秒杀(订单5分钟没有支付，自动取消，使用rabbitMQ延迟队列)

    // 消费者端-秒杀活动-秒杀(队列里面需要还库存)
}
