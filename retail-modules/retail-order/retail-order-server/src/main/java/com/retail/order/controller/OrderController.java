package com.retail.order.controller;

import java.util.List;


import com.retail.common.result.Result;
import com.retail.order.domain.OrderEntity;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.retail.order.service.OrderService;


/**
 * 订单表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 18:50:55
 */
@RestController
@RequestMapping("order/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedissonClient redissonClient;


    /**
     * 生成拼团订单
     * @param spuId
     * @return
     */
    @PostMapping("/createSkeillOrder/{spuId}")
    public Result<OrderEntity> createSkeillOrder(@PathVariable("spuId") Long spuId){

        if (spuId==null){
            return Result.error("请点击下单");
        }

        Result<OrderEntity> orderEntityResult= orderService.createSkeillOrder(spuId);


        return orderEntityResult;


    }



    /**
     * 修改订单地址
     * @param orderEntity
     * @return
     */
    @PostMapping("/updateOrderAddress")
    public Result updateOrderAddress(@RequestBody OrderEntity orderEntity){



        if (orderEntity.getId()==null){
            return  Result.error("请选择订单");
        }
        if (orderEntity.getAddressId()==null){
            return  Result.error("请选择地址");
        }


       Result result= orderService.updateOrderAddress(orderEntity);



       return  result;
    }



}
