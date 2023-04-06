package com.retail.order.controller;

import java.util.List;


import com.baomidou.mybatisplus.extension.api.R;
import com.retail.common.domain.vo.OrderEntityVo;
import com.retail.common.result.Result;
import com.retail.order.domain.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    // order/order/orderInsert
    @PostMapping("/orderInsert")
    public Result orderInsert(@RequestBody OrderEntityVo orderEntityVo){
        return orderService.orderInsert(orderEntityVo);
    }
    // order/order/getOrderListSeckill
    @PostMapping("/getOrderList")
    public Result<List<OrderEntity>> getOrderList(){
        return orderService.getOrderList();
    }

    // order/order/findByOrderSn/
    @GetMapping("/findByOrderSn/{orderSn}")
    public Result findByOrderSn(@PathVariable("orderSn") String orderSn){
        return orderService.findByOrderSn(orderSn);
    }
}
