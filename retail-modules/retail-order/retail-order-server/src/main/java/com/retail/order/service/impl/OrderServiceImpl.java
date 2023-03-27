package com.retail.order.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.retail.common.constant.TokenConstants;

import com.retail.common.domain.vo.*;
import com.retail.common.result.Result;
import com.retail.common.utils.JwtUtils;
import com.retail.order.domain.OrderEntity;
import com.retail.order.feign.ShopFeign;
import com.retail.order.feign.UserFeign;
import com.retail.order.mapper.OrderMapper;
import com.retail.order.service.OrderService;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderEntity> implements OrderService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ShopFeign shopFeign;

    @Autowired
    private UserFeign userFeign;





    @Autowired
    private RedissonClient redissonClient;


    @Override
    public Result createSkeillOrder(OrderEntity orderEntity)  {

//        RateLimiter rateLimiter = RateLimiter.create(1);
//       try{
//
//               String time = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
//               System.out.println(time + ":" + rateLimiter.tryAcquire());
//               Thread.sleep(250);
//
//       }catch (Exception e){
//           e.printStackTrace();
//       }

        String s = redisTemplate.opsForValue().get("seckill_spu" + orderEntity.getSeckillId());
        SeckillSpuVo seckillSpuVo = JSON.parseObject(s, SeckillSpuVo.class);
        Long spuId = seckillSpuVo.getSpuId();



       // 查询库存
        InventoryVo inventoryVo= shopFeign.selectInventory(spuId);
        Integer inventoryCount = inventoryVo.getInventoryCount();
        if (inventoryCount==0){
            return Result.error(502,"商品已被抢空");
        }


        UserEntityVo userEntityVo = this.userInfo();
        //登录人设置
        orderEntity.setUserId(userEntityVo.getId());
        //订单状态(待支付)
        orderEntity.setStatus(1);
        //类型(拼团)
        orderEntity.setType(2);
        //生成订单号
        String numbers = RandomUtil.randomNumbers(11);
        Long userId = userEntityVo.getId();
        String orderSn="";
        String substring = userId.toString().substring(15);
        orderSn=numbers+substring;
        orderEntity.setOrderSn(orderSn);
        //生成订单时间
        orderEntity.setCreateTime(new Date());
        //默认地址
        List<AddressVo> addressVoList=userFeign.selectOrderAddress(userEntityVo.getId());

        if (addressVoList==null){
            return Result.error(502,"默认地址查询失败");
        }

        List<AddressVo> addressVos = addressVoList.stream().filter(c -> c.getDefaultStatus() == 0).collect(Collectors.toList());

        addressVos.stream().forEach(c->{
            orderEntity.setName(c.getName());
            orderEntity.setPhone(c.getPhone());
            String address=c.getProvince()+c.getCity()+c.getRegion()+c.getAddress();
            orderEntity.setAddress(address);
        });





        this.baseMapper.insert(orderEntity);


        return Result.success("订单添加成功");


    }

    @Override
    public Result updateOrderAddress(OrderEntity orderEntity) {

        UserEntityVo userEntityVo = this.userInfo();

        List<AddressVo> addressVoList=userFeign.selectOrderAddress(userEntityVo.getId());
        List<AddressVo> addressVos = addressVoList.stream().filter(c -> c.getId()==orderEntity.getAddressId()).collect(Collectors.toList());

        addressVos.stream().forEach(c->{
            orderEntity.setName(c.getName());
            orderEntity.setPhone(c.getPhone());
            String address=c.getProvince()+c.getCity()+c.getRegion()+c.getAddress();
            orderEntity.setAddress(address);
        });

        this.baseMapper.updateById(orderEntity);

        return Result.success("订单地址修改成功");
    }


    public UserEntityVo userInfo(){
        String token = request.getHeader("token");
        String userKey = JwtUtils.getUserKey(token);
        String s = redisTemplate.opsForValue().get(TokenConstants.LOGIN_TOKEN_KEY + userKey);
        UserEntityVo user = JSON.parseObject(s, UserEntityVo.class);
        return user;
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
