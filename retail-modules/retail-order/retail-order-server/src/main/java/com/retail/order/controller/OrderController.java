package com.retail.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.retail.common.result.PageResult;
import com.retail.common.result.Result;
import com.retail.order.domain.OrderEntity;
import com.retail.order.domain.request.OrderRequest;
import com.retail.order.mapper.OrderMapper;
import com.retail.order.service.OrderService;
import com.retail.order.vo.OrderEntityVo;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


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
    @Resource
    private OrderMapper orderMapper;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 订单列表
     */
    @PostMapping("/list")
    public Result<PageResult<OrderEntityVo>> list(@RequestBody OrderRequest request){
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<OrderEntityVo> list = orderService.orderList(request);
        PageInfo<OrderEntityVo> pageInfo = new PageInfo<>(list);
        Result<PageResult<OrderEntityVo>> result = PageResult.toResult(pageInfo.getTotal(), list);
        return result;
    };

//    /**
//     * 生成拼团订单
//     * @param orderEntity
//     * @return
//     */
//    @PostMapping("/createSkeillOrder")
//    public Result createSkeillOrder(@RequestBody OrderEntity orderEntity){
//
//        if (orderEntity==null){
//            return Result.error("请点击下单");
//        }
//
//        Result result= orderService.createSkeillOrder(orderEntity);
//
//        return Result.success(result);
//
//
//    }

//    /**
//     * 修改订单地址
//     * @param orderEntity
//     * @return
//     */
//    @PostMapping("/updateOrderAddress")
//    public Result updateOrderAddress(@RequestBody OrderEntity orderEntity){
//
//        if (orderEntity.getId()==null){
//            return  Result.error("请选择订单");
//        }
//        if (orderEntity.getAddressId()==null){
//            return  Result.error("请选择地址");
//        }
//       Result result= orderService.updateOrderAddress(orderEntity);
//       return  result;
//    }

    /**
     * 订单添加
     * @param orderEntity
     * @return
     */
    @PostMapping("/add")
    public Long addOrder(@RequestBody OrderEntity orderEntity){
        int insert = orderMapper.insert(orderEntity);
        if (insert>0){
            return orderEntity.getId();
        }
        return null;
    }

    /**
     * 订单详情
     */
    @PostMapping("/orderFindById/{id}")
    public Result<OrderEntity> orderFindById(@PathVariable Long id){
        List<OrderEntity> orderEntity = orderMapper.selectList(new QueryWrapper<OrderEntity>().lambda().eq(OrderEntity::getId,id));
        return Result.success(orderEntity.get(0));
    }

    /**
     * 订单号查询订单信息
     */
    @PostMapping("findByOrderSn/{orderSn}")
    public Result<OrderEntity> orderFindByOrderSn(@PathVariable("orderSn") String orderSn){
        List<OrderEntity> orderEntity = orderMapper.selectList(new QueryWrapper<OrderEntity>().lambda().eq(OrderEntity::getOrderSn,orderSn));
        return Result.success(orderEntity.get(0));
    }

    /**
     * 订单状态修改  (支付成功后)
     */
    @PostMapping("updateOrderStatus/{orderSn}")
    public Result updateOrderStatus(@PathVariable("orderSn") String orderSn){
        OrderEntity orderEntity = new OrderEntity();
        //结账时间
        orderEntity.setRealityTime(new Date());
        //支付状态 2支付成功
        orderEntity.setStatus(2);
        int update = orderMapper.update(orderEntity, new QueryWrapper<OrderEntity>().lambda().eq(OrderEntity::getOrderSn, orderSn));
        if (update==0){
            return Result.error("修改失败");
        }
        return Result.success("修改成功");
    }



}
