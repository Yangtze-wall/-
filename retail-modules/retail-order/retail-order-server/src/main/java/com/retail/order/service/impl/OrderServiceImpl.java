package com.retail.order.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.vo.*;
import com.retail.common.result.Result;
import com.retail.common.utils.JwtUtils;
import com.retail.order.config.PayVo;
import com.retail.order.domain.OrderEntity;
import com.retail.order.feign.ShopFeignService;
import com.retail.order.feign.UserFeignService;
import com.retail.order.mapper.OrderMapper;
import com.retail.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderEntity> implements OrderService {


    @Autowired
    private ShopFeignService shopFeignService;
    @Autowired
    private UserFeignService userFeignService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public UserEntityVo userInfo(){
        String token = request.getHeader("token");
        String userKey = JwtUtils.getUserKey(token);
        String s = redisTemplate.opsForValue().get(TokenConstants.LOGIN_TOKEN_KEY + userKey);
        UserEntityVo user = JSON.parseObject(s, UserEntityVo.class);
        return user;
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result orderInsert(OrderEntityVo orderEntityVo) {
        // 判断 订单里面有没有这个 人 买的砍价物品  userId bargainId
        Long bargainId = orderEntityVo.getBargainId();

        // 判断该用户积分是否足够

        Result<SkuEntityVo> skuEntryResult = shopFeignService.findBySkuEntry(orderEntityVo.getSpuId());
        SkuEntityVo skuEntityVo = skuEntryResult.getData();
        //生成订单
        OrderEntity orderEntity = new OrderEntity();
        //用户id   Long userId;
        orderEntity.setUserId(userInfo().getId());
        //商品单号    String orderSn;
        String orderSn = RandomUtil.randomNumbers(13);
        orderEntity.setOrderSn(orderSn);
        //总价钱   BigDecimal totalAmount;
        orderEntity.setTotalAmount(skuEntityVo.getSkuPrice());
        //积分抵扣金额   BigDecimal integrationAmount;
        BigDecimal integrationAmount = BigDecimal.valueOf(orderEntityVo.getIntegration()).multiply(BigDecimal.valueOf(0.001));
        orderEntity.setIntegrationAmount(integrationAmount);
        //优惠券抵扣金额   BigDecimal couponAmount;
        Long couponId = orderEntityVo.getCouponId();
        Result<StoreCouponEntityVo> byIdStoreCoupon = shopFeignService.findByIdStoreCoupon(couponId);
        StoreCouponEntityVo storeCouponEntityVo = byIdStoreCoupon.getData();
        BigDecimal money = storeCouponEntityVo.getMoney();
        orderEntity.setCouponAmount(money);
        //运费金额   BigDecimal freightAmount;
        orderEntity.setFreightAmount(BigDecimal.valueOf(0));
        //应付价钱    BigDecimal payAmount;
        BigDecimal subtract = skuEntityVo.getSkuPrice().subtract(integrationAmount).subtract(money).subtract(BigDecimal.valueOf(0));
        orderEntity.setPayAmount(subtract);
        //状态(1.待支付 2.支付成功 3.支付失败)      Integer status;
        orderEntity.setStatus(0);
        //快递公司id   Long corporationId;
        orderEntity.setCorporationId(orderEntityVo.getCorporationId());
        // 收件人 id
        Long addressId = orderEntityVo.getAddressId();
        orderEntity.setAddressId(addressId);
        //收件人姓名   String name;
        Result<UserAddressEntityVo> byIdAddress = userFeignService.findByIdAddress(addressId);
        UserAddressEntityVo userAddressEntityVo = byIdAddress.getData();
        orderEntity.setName(userAddressEntityVo.getName());
        //电话   String phone;
        orderEntity.setPhone(userAddressEntityVo.getPhone());
        //收货地址    String address;
        orderEntity.setAddress(userAddressEntityVo.getAddress());
        //备注    String remark;
        orderEntity.setRemark(orderEntityVo.getRemark());
        //优惠券id   Long couponId;
        orderEntity.setCouponId(couponId);
        //下单时间   Date createTime;
        orderEntity.setCreateTime(new Date());
        // 砍价id
        orderEntity.setBargainId(orderEntityVo.getBargainId());
        // type
        orderEntity.setType(4);
        baseMapper.insert(orderEntity);
        // 修改用户表的积分

        //　删除　优惠券中间表　用户的优惠券　修改状态 is_del


        return Result.success("下单成功");
    }

    @Override
    public String handlePayResult(PayVo vo) {
        return null;
    }
}
