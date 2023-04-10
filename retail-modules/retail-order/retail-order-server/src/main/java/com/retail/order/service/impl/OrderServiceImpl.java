package com.retail.order.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.retail.common.utils.StringUtils;
import com.retail.order.domain.OrderEntity;
import com.retail.order.domain.request.OrderRequest;
import com.retail.order.feign.ShopFeign;
import com.retail.order.feign.UserFeign;
import com.retail.order.mapper.OrderMapper;
import com.retail.order.service.OrderService;
import com.retail.order.vo.OrderEntityVo;
import com.retail.order.vo.SkuEntity;
import com.retail.order.vo.SpuEntity;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderEntity> implements OrderService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ShopFeign shopFeign;

    @Autowired
    private UserFeign userFeign;

    @Resource
    private OrderMapper orderMapper;


    @Autowired
    private RedissonClient redissonClient;

//
//    @Override
//    public Result createSkeillOrder(OrderEntity orderEntity)  {
//
////        RateLimiter rateLimiter = RateLimiter.create(1);
////       try{
////
////               String time = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
////               System.out.println(time + ":" + rateLimiter.tryAcquire());
////               Thread.sleep(250);
////
////       }catch (Exception e){
////           e.printStackTrace();
////       }
//
//        String s = redisTemplate.opsForValue().get("seckill_spu" + orderEntity.getSeckillId());
//        SeckillSpuVo seckillSpuVo = JSON.parseObject(s, SeckillSpuVo.class);
//        Long spuId = seckillSpuVo.getSpuId();
//
//
//
//       // 查询库存
//        InventoryVo inventoryVo= shopFeign.selectInventory(spuId);
//        Integer inventoryCount = inventoryVo.getInventoryCount();
//        if (inventoryCount==0){
//            return Result.error(502,"商品已被抢空");
//        }
//
//
//        UserEntityVo userEntityVo = this.userInfo();
//        //登录人设置
//        orderEntity.setUserId(userEntityVo.getId());
//        //订单状态(待支付)
//        orderEntity.setStatus(1);
//        //类型(拼团)
//        orderEntity.setType(2);
//        //生成订单号
//        String numbers = RandomUtil.randomNumbers(11);
//        Long userId = userEntityVo.getId();
//        String orderSn="";
//        String substring = userId.toString().substring(15);
//        orderSn=numbers+substring;
//        orderEntity.setOrderSn(orderSn);
//        //生成订单时间
//        orderEntity.setCreateTime(new Date());
//        //默认地址
//        List<AddressVo> addressVoList=userFeign.selectOrderAddress(userEntityVo.getId());
//
//        if (addressVoList==null){
//            return Result.error(502,"默认地址查询失败");
//        }
//
//        List<AddressVo> addressVos = addressVoList.stream().filter(c -> c.getDefaultStatus() == 0).collect(Collectors.toList());
//
//        addressVos.stream().forEach(c->{
//            orderEntity.setName(c.getName());
//            orderEntity.setPhone(c.getPhone());
//            String address=c.getProvince()+c.getCity()+c.getRegion()+c.getAddress();
//            orderEntity.setAddress(address);
//        });
//
//
//
//
//
//        this.baseMapper.insert(orderEntity);
//
//
//        return Result.success("订单添加成功");
//
//
//    }
//
//    @Override
//    public Result updateOrderAddress(OrderEntity orderEntity) {
//
//        UserEntityVo userEntityVo = this.userInfo();
//
//        List<AddressVo> addressVoList=userFeign.selectOrderAddress(userEntityVo.getId());
//        List<AddressVo> addressVos = addressVoList.stream().filter(c -> c.getId()==orderEntity.getAddressId()).collect(Collectors.toList());
//
//        addressVos.stream().forEach(c->{
//            orderEntity.setName(c.getName());
//            orderEntity.setPhone(c.getPhone());
//            String address=c.getProvince()+c.getCity()+c.getRegion()+c.getAddress();
//            orderEntity.setAddress(address);
//        });
//
//        this.baseMapper.updateById(orderEntity);
//
//        return Result.success("订单地址修改成功");
//    }

    @Override
    public List<OrderEntityVo> orderList(OrderRequest request) {
        //根据用户ID查询订单列表
        List<OrderEntity> orderEntities = orderMapper.selectList(new QueryWrapper<OrderEntity>().lambda().eq(OrderEntity::getUserId, request.getUserId()));
//        订单列表信息

        List<OrderEntityVo> collect = orderEntities.stream().map(c -> {
            OrderEntityVo orderEntityVo = new OrderEntityVo();
            //查询商品信息
            SpuEntity spu = orderMapper.selectFindByIdSpu(c.getSpuId());
            //查询商品规格
            SkuEntity sku = orderMapper.selectFindByIdSku(c.getSkuId());
            //添加到订单列表
            orderEntityVo.setId(c.getId());
            orderEntityVo.setUserId(c.getUserId());
            orderEntityVo.setImgUrl(sku.getSkuImage());
            orderEntityVo.setDesc(sku.getSkuSubhead());
            orderEntityVo.setSpuaNme(spu.getSpuName());
            orderEntityVo.setStatus(c.getStatus());
            orderEntityVo.setTotalPrice(c.getTotalAmount());
            orderEntityVo.setOrderSn(c.getOrderSn());
            //优惠金额计算
            if (c.getCouponAmount() != null) {//优惠券抵扣金额
                orderEntityVo.setCouponPrice(orderEntityVo.getCouponPrice().add(c.getCouponAmount()));
            }
            if (c.getIntegrationAmount() != null) {//积分抵扣金额
                orderEntityVo.setCouponPrice(orderEntityVo.getCouponPrice().add(c.getIntegrationAmount()));
            }
            orderEntityVo.setPayPrice(c.getPayAmount());
            return orderEntityVo;
        }).collect(Collectors.toList());
        //根据商品名称查询商品详情
        if (!StringUtils.isNull(request.getSpuName())){
            collect.stream().filter(c->c.getSpuaNme().matches(".*"+request.getSpuName()+".*"));
        }
        //根据商品简介查询商品详情
        if (!StringUtils.isNull(request.getSpuDesc())){
            collect.stream().filter(c->c.getDesc().matches(".*" + request.getSpuDesc() + ".*"));
        }
        //根据支付状态进行排序  1待支付  2支付成功  3支付失败
        List<OrderEntityVo> collect1 = collect.stream().sorted(Comparator.comparing(OrderEntityVo::getStatus)).collect(Collectors.toList());
        return collect1;
    }


//    public Result getLock(Long id){
//
//        RSemaphore semaphore = redissonClient.getSemaphore("order_skeill" + id);
//        boolean flag = semaphore.trySetPermits(1);
//        if (!flag){
//            return Result.error("获取库存失败");
//        }
//
//        return Result.success("还有库存");
//
//    }

}
