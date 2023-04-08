package com.retail.order.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.vo.*;
import com.retail.common.result.Result;
import com.retail.common.utils.JwtUtils;
import com.retail.common.utils.StringUtils;
import com.retail.order.domain.OrderEntity;
import com.retail.order.feign.ShopFeignService;
import com.retail.order.feign.UserFeignService;
import com.retail.order.mapper.OrderMapper;
import com.retail.order.service.ExpressageService;
import com.retail.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderEntity> implements OrderService {

    @Autowired
    private UserFeignService userFeignService;


    @Autowired
    private ShopFeignService shopFeignService;
    @Override
    public Result getOrderList() {
        List<OrderEntity> orderEntityList =
                baseMapper.selectList(new QueryWrapper<OrderEntity>().lambda().eq(OrderEntity::getUserId, userInfo().getId()));
        return Result.success(orderEntityList);
    }

    @Override
    public Result<OrderEntity> findByOrderSn(String orderSn) {
        OrderEntity orderEntity = this.baseMapper.selectOne(new QueryWrapper<OrderEntity>().lambda().eq(OrderEntity::getOrderSn, orderSn));

        return Result.success(orderEntity);
    }

    @Override
    public Result insertOrder(OrderEntityVo orderEntityVo) {

        //雪花算法
        //用户id   Long userId;
        Long id = userInfo().getId();
        OrderEntity orderEntity = new OrderEntity();
        //用户id
        orderEntity.setUserId(id);
        // 生成  订单 OrderSn
        String idString = id.toString();
        if (idString.length()<5){
            idString=  StringUtils.leftPad(idString, 0, "0");
        }else {
            idString= idString.substring(idString.length()-5);
        }
        idString = StringUtils.leftPad(idString, 8, "0");
        String substring = IdUtil.getSnowflake(1, 1).nextIdStr().substring(6);
        String orderSn = substring+ StringUtils.leftPad(idString,8,"0");
        //订单号
        orderEntity.setOrderSn(orderSn);
        //根据用户id 获取用户地址信息
        Long addressId = orderEntityVo.getAddressId();
        Result<UserAddressEntityVo> userAddressEntityVoResult = userFeignService.findUserAddressById(addressId);
        UserAddressEntityVo userAddressEntityVo = userAddressEntityVoResult.getData();
        if (userAddressEntityVo==null){
            return Result.error("获取用户信息地址失败");
        }
        orderEntity.setAddressId(orderEntityVo.getAddressId());
        orderEntity.setName(userAddressEntityVo.getName());
        orderEntity.setPhone(userAddressEntityVo.getPhone());
        orderEntity.setAddress(userAddressEntityVo.getAddress());

        //快递公司id
        orderEntity.setCorporationId(orderEntityVo.getCorporationId());
        // skuId 获取 sku 信息
        Result<ProductVo> productVoResult = shopFeignService.findBySkuId(orderEntity.getSkuId());
        ProductVo productVo = productVoResult.getData();
        if (productVo==null){
            return Result.error("获取商品信息失败");
        }
        orderEntity.setSkuId(orderEntityVo.getSkuId());
        orderEntity.setTotalAmount(productVo.getSkuPrice());
        orderEntity.setSpuId(productVo.getSpuId());

        //优惠卷id
        orderEntity.setCouponId(orderEntityVo.getCouponId());
        Result<StoreCouponEntityVo> storeCouponEntityVoResult = shopFeignService.findStoreCouponById(orderEntityVo.getCouponId());
        StoreCouponEntityVo storeCouponEntityVo = storeCouponEntityVoResult.getData();
        if (storeCouponEntityVo==null){
            return Result.error("获取优惠卷信息失败");
        }
        //优惠券抵扣金额
        orderEntity.setCouponAmount(storeCouponEntityVo.getMoney());
        // status 状态(1.待支付 2.支付成功 3.支付失败)
        orderEntity.setStatus(1);
        //type类型(1.正常,2.拼团,3秒杀 4.砍价)
        orderEntity.setType(1);
        //运费
        orderEntity.setFreightAmount(BigDecimal.ZERO);
        //积分抵扣金额 (100积分->1元)
        orderEntity.setIntegrationAmount(BigDecimal.ZERO);
        //备注
        orderEntity.setRemark(orderEntityVo.getRemark());
        //生成下单时间
        orderEntity.setCreateTime(new Date());
        this.baseMapper.insert(orderEntity);

        return Result.success();
    }

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
}
