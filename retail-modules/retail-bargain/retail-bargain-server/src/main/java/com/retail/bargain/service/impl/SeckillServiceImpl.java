package com.retail.bargain.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.retail.bargain.domain.SeckillEntity;
import com.retail.bargain.domain.request.PaymentEntity;
import com.retail.bargain.domain.request.SeckillRequest;
import com.retail.bargain.domain.request.StartSeckill;
import com.retail.bargain.feign.OrderFeign;
import com.retail.bargain.mapper.SeckillMapper;
import com.retail.bargain.service.SeckillService;
import com.retail.bargain.util.AlipayUtil;
import com.retail.bargain.vo.AddressVo;
import com.retail.bargain.vo.OrderVo;
import com.retail.bargain.vo.SkuEntity;
import com.retail.bargain.vo.SpuEntity;
import com.retail.common.domain.vo.InventoryVo;
import com.retail.common.domain.vo.SeckillSpuVo;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.result.Result;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @ProjectName: retail-cloud
 * @Package: com.retail.colonel.service.impl
 * @ClassName: SeckillServiceImpl
 * @Author: 2766395184
 * @Description: 秒杀 逻辑层
 * @Date: 2023/3/25 9:57
 * @Version: 1.0
 */
@Service("seckillService")
@Log4j2
public class SeckillServiceImpl extends ServiceImpl<SeckillMapper, SeckillEntity> implements SeckillService {


    @Resource
    private SeckillMapper seckillMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private AlipayUtil alipayUtil;

    @Autowired
    private OrderFeign orderFeign;


//    @Override
//    public Result<SeckillSpuVo> itemDetail(Long id) {
//
//        //查询商品(秒杀开始时间)
//        String s = redisTemplate.opsForValue().get("seckill_spu" + id);
//        SeckillSpuVo seckillSpuVo = JSON.parseObject(s, SeckillSpuVo.class);
//        //查询库存
//        SkuInventoryVo skuInventoryVo = seckillMapper.selectSkuInventory(seckillSpuVo.getSpuId());
//
//
//        return Result.success(seckillSpuVo);
//    }

    @Override
    public List<SeckillEntity> seckillList(SeckillRequest request) {
        int i = 0;
        ArrayList<SeckillEntity> seckillEntities = new ArrayList<>();
        String seckillList = redisTemplate.opsForValue().get("seckill_list");
        List<SeckillEntity> list = JSONObject.parseArray(seckillList, SeckillEntity.class);
        //根据名称查询
        if (request.getSeckillName() != null && !"".equals(request.getSeckillName())) {
            list.stream().filter(c -> c.getSeckillHeadline().matches(".*" + request.getSeckillName() + ".*")).forEach(c -> seckillEntities.add(c));
            i++;
        }
        //时间区间查询
        if (request.getStartTime() != null || request.getEndTime() != null) {
            i++;
            if (request.getEndTime() == null) {
                List<SeckillEntity> collect = list.stream().filter(c -> c.getSeckillStartTime().after(request.getStartTime())).collect(Collectors.toList());
                collect.forEach(c -> seckillEntities.add(c));
            } else if (request.getStartTime() == null) {
                List<SeckillEntity> collect = list.stream().filter(c -> c.getSeckillStartTime().before(request.getEndTime())).collect(Collectors.toList());
                collect.forEach(c -> seckillEntities.add(c));
            } else {
                List<SeckillEntity> collect = list.stream().filter(c -> c.getSeckillStartTime().after(request.getStartTime()) && c.getSeckillStartTime().before(request.getEndTime())).collect(Collectors.toList());
                collect.forEach(c -> seckillEntities.add(c));
            }
        }
        if (i == 0) {
            list.stream().forEach(c -> seckillEntities.add(c));
        }
        List<SeckillEntity> collect = seckillEntities.stream().map(c -> {
            //sku
            SkuEntity skuEntity = seckillMapper.findByIdSku(c.getSkuId());
            InventoryVo inventoryVo = seckillMapper.findByIdInventory(skuEntity.getSpuId());
            SeckillEntity seckillEntity = new SeckillEntity();
            BeanUtil.copyProperties(c, seckillEntity);
            seckillEntity.setSeckillResidueCount(inventoryVo.getInventoryCount() - inventoryVo.getInventoryLock());
            return seckillEntity;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public Result seckillAdd(SeckillEntity entity) {
        if (entity.getSkuId() == null) {
            return Result.error("商品id不能为空");
        }
        if (entity.getSlideShow() == null) {
            return Result.error("秒杀轮播图不能为空");
        }
        if (entity.getSeckillHeadline() == null) {
            return Result.error("商品秒杀标题不能为空");
        }
        if (entity.getSeckillPrice() == null) {
            return Result.error("秒杀价格不能为空");
        }
        if (entity.getSeckillStartTime() == null) {
            return Result.error("秒杀开始时间不能为空");
        }
        if (entity.getSeckillEndTime() == null) {
            return Result.error("秒杀结束时间不能为空");
        }
        if (entity.getPostFree() == null) {
            return Result.error("是否包邮不能为空");
        }
        if (entity.getSeckillResidueCount() == null) {
            return Result.error("秒杀库存不能为空");
        }
        if (entity.getSeckillLimit() == null) {
            return Result.error("秒杀限购数量不能为空");
        }

        //秒杀创建时间为当前时间
        entity.setSeckillCreateTime(new Date());

        int insert = seckillMapper.insert(entity);
        if (insert > 0) {
            return Result.success("添加成功");
        }
        return Result.error("添加失败");
    }

    @Override
    public Result start(StartSeckill seckill) {
        //分布式锁
        RLock lock = redissonClient.getLock("seckill_lock" + seckill.getUserId());
        boolean b = lock.tryLock();
        if (!b) {
            //获取锁失败
            return Result.error("网络繁忙请稍后重试");
        }
        try {
            lock.lock(4, TimeUnit.SECONDS);
            //查询要秒杀的商品
            List<SeckillEntity> list = seckillMapper.selectList(new QueryWrapper<SeckillEntity>().lambda().eq(SeckillEntity::getId, seckill.getSeckillId()));
            SeckillEntity seckillEntity = list.get(0);
            //sku
            SkuEntity skuEntity = seckillMapper.findByIdSku(seckillEntity.getSkuId());
            //根据商品主键id查询商品信息
            SpuEntity spuEntity = seckillMapper.findByIdSpu(skuEntity.getSpuId());
            //查询订单表,查看用户是否购买过
            List<OrderVo> findOrder = seckillMapper.findOrderBySeckillIdUserId(seckill.getSeckillId(), seckill.getUserId());
            //根据spu搜索库存
            InventoryVo inventoryVo = seckillMapper.findByIdInventory(spuEntity.getId());
            Result result = new Result();
            for (int i = 0; i < findOrder.size(); i++) {
                if (findOrder.get(i).getStatus() == 1) {
                    result.setCode(500);
                    result.setMsg("您的订单已生成,请及时支付");
                }
                if (findOrder.get(i).getStatus() == 2) {
                    result.setCode(500);
                    result.setMsg("每位用户限购一次");
                }
            }
            if (result.getCode()!=0) {
                return result;
            }

            //用户信息
            UserEntityVo user = seckillMapper.userLogin(seckill.getUserId());
            //收货地址信息
            AddressVo addressVo = seckillMapper.selectAddress(seckill.getAddressId());

            //开始秒杀
            //是否在活动时间段
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            if (calendar.getTime().before(seckillEntity.getSeckillStartTime()) || calendar.getTime().after(seckillEntity.getSeckillEndTime())) {
                return Result.error("秒杀商品不在活动时间范围内");
            }
            //限购商品
            if (seckillEntity.getSeckillLimit() < seckill.getCount()) {
                return Result.error("每人最多限购数量:" + seckillEntity.getSeckillLimit());
            }

            //判断库存是否足够 购买数量<库存数量-锁库存数量
            if (inventoryVo.getInventoryCount() - inventoryVo.getInventoryLock() < seckill.getCount()) {
                return Result.error("库存不足");
            }

            //减秒杀表库存
            seckillEntity.setSeckillResidueCount(Math.toIntExact(seckillEntity.getSeckillResidueCount() - seckill.getCount()));
            seckillMapper.updateById(seckillEntity);
            //锁库存
            seckillMapper.inventoryLock(inventoryVo.getId(), seckill.getCount());

            //保存订单和订单明细
            //订单添加
            OrderVo orderVo = new OrderVo();
            orderVo.setUserId(user.getId());
            orderVo.setOrderSn(RandomUtil.randomNumbers(19));
            orderVo.setTotalAmount(seckillEntity.getSeckillPrice());
            orderVo.setIntegrationAmount(null);
            orderVo.setFreightAmount(null);
            if (seckillEntity.getPostFree() == 2) {
                orderVo.setFreightAmount(BigDecimal.valueOf(10));
            }
            if (orderVo.getFreightAmount() != null) {
                orderVo.setPayAmount(seckillEntity.getSeckillPrice().add(orderVo.getFreightAmount()));
            }
            orderVo.setStatus(1);
            orderVo.setCorporationId(1L);
            orderVo.setName(addressVo.getName());
            orderVo.setPhone(addressVo.getPhone());
            orderVo.setAddress(addressVo.getAddress());
            orderVo.setRemark(seckill.getRemark());
            orderVo.setAddressId(addressVo.getId());
            orderVo.setCouponId(null);
            orderVo.setCreateTime(new Date());
            orderVo.setPayAmount(seckillEntity.getSeckillPrice());
            orderVo.setType(3);
            orderVo.setSeckillId(seckillEntity.getId());
            orderVo.setSpuId(skuEntity.getSpuId());
            orderVo.setSkuId(skuEntity.getId());
            //添加订单
            Long orderId = orderFeign.addOrder(orderVo);
            seckill.setOrderId(orderId);
            //发送延迟队列15分钟失效 修改订单状态  回滚库存
            rabbitTemplate.convertAndSend("seckill_queue_exchange", "seckill_queue_queue_key", JSONObject.toJSONString(seckill));

//            //调用支付宝接口  返回请求地址
//            PayRequest payRequest = new PayRequest();
//            payRequest.setOutTradeNo(orderVo.getOrderSn());
//            payRequest.setSubject(spuEntity.getSpuName());
//            payRequest.setTotalAmount(orderVo.getTotalAmount());
//            alipayUtil.aliPay(payRequest);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //解锁
            lock.forceUnlock();
        }
        return Result.success("订单已生成,请支付");
    }


    @Override
    public Result pay(PaymentEntity paymentEntity) {
        //用户ID查询用户信息
        UserEntityVo userEntityVo = seckillMapper.userLogin(paymentEntity.getUserId());
        //订单号查订单
//        List<OrderVo> orderVo = seckillMapper.findByOrderSn();

        //调用支付宝接口
//        alipayUtil.aliPay(orderVo.getOrderSn(), orderVo.getPayAmount(), UUID.randomUUID().toString().replaceAll("-", ""));
        return null;
    }

    @Override
    public SeckillSpuVo itemDetail(Long id) {
        String seckillList = redisTemplate.opsForValue().get("seckill_list");
        List<SeckillEntity> list = JSONObject.parseArray(seckillList, SeckillEntity.class);
        List<SeckillEntity> collect = list.stream().filter(c -> c.getSkuId().equals(id)).collect(Collectors.toList());
        //sku
        SkuEntity skuEntity = seckillMapper.findByIdSku(collect.get(0).getSkuId());
        //根据商品主键id查询商品信息
        SpuEntity spuEntity = seckillMapper.findByIdSpu(skuEntity.getSpuId());

        SeckillSpuVo spuVo = new SeckillSpuVo();
        BeanUtil.copyProperties(spuEntity, spuVo);
        BeanUtil.copyProperties(collect.get(0), spuVo);
//        seckillEntityVo.setSpuName(spuEntity.getSpuName());
        log.info(spuVo);

        return spuVo;
    }


}
