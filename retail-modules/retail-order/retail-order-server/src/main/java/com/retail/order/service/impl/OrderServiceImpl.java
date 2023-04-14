package com.retail.order.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.vo.*;
import com.retail.common.result.Result;
import com.retail.common.utils.JwtUtils;
import com.retail.common.utils.StringUtils;
import com.retail.order.domain.CartEntity;
import com.retail.order.domain.OrderEntity;
import com.retail.order.feign.ShopFeignService;
import com.retail.order.feign.UserFeignService;
import com.retail.order.mapper.CartMapper;
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
import java.util.List;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderEntity> implements OrderService {

    @Autowired
    private UserFeignService userFeignService;

    @Autowired
    private ShopFeignService shopFeignService;

    @Autowired
    private CartMapper cartMapper;

    @Override
    public Result getOrderList() {
        List<OrderEntity> orderEntityList =
                baseMapper.selectList(new QueryWrapper<OrderEntity>().lambda().eq(OrderEntity::getUserId, userInfo().getId()));
        return Result.success(orderEntityList);
    }

    @Override
    public Result<OrderEntity> findByOrderSn(String orderSn) {
        LambdaQueryWrapper<OrderEntity> eq = new QueryWrapper<OrderEntity>().lambda().eq(OrderEntity::getOrderSn, orderSn);
        OrderEntity orderEntity = this.baseMapper.selectOne(eq);

        return Result.success(orderEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result insertOrder(OrderEntityVo orderEntityVo) {
        OrderEntity orderEntity = new OrderEntity();

        //雪花算法
        //用户id   Long userId;
        Long id = userInfo().getId();
        //获取用户积分
        Result<UserEntityVo> userEntityVoResult = userFeignService.findUserById(id);
        UserEntityVo userEntityVo = userEntityVoResult.getData();
        if (userEntityVo==null){
            return Result.error("获取用户信息失败");
        }
        //用户积分
        Integer integration = userEntityVo.getIntegration();
        //积分数小于100分 积分兑换为0
        if (integration.intValue()<100){
            orderEntity.setIntegrationAmount(BigDecimal.ZERO);
        }else {
            //积分抵扣金额 (100积分->1元) 最多抵扣积分100分
            orderEntity.setIntegrationAmount(BigDecimal.ONE);//一元
            //积分表记录 添加积分记录（扣减积分）
            IntegrationHistoryEntityVo integrationHistoryEntityVo = new IntegrationHistoryEntityVo();
            integrationHistoryEntityVo.setCount(100);
            integrationHistoryEntityVo.setUserId(id);
            integrationHistoryEntityVo.setCreateTime(new Date());
            integrationHistoryEntityVo.setRemark("用户下订单抵扣金额,扣减积分");
            //来源类型（1签到，2购买，3兑换优惠券）
            integrationHistoryEntityVo.setSourceType(2);
            userFeignService.insertIntegrationByOrder(integrationHistoryEntityVo);
            //用户积分表扣减积分
            userEntityVo.setIntegration(integration-100);
            userFeignService.updateUser(userEntityVo);
        }
        //用户id
        orderEntity.setUserId(id);
        // 生成  订单 OrderSn
        String idString = id.toString();
        if (idString.length()<5){
            idString=  StringUtils.leftPad(idString, 0, "0");
        }else {
            idString= idString.substring(idString.length()-5);
        }
        idString = StringUtils.leftPad(idString, 5, "0");
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
        //根据购物车id 查询 购物车数据
        orderEntity.setCartId(orderEntityVo.getCartId());
        CartEntity cartEntity = cartMapper.selectById(orderEntityVo.getCartId());



//        Result<ProductVo> productVoResult = shopFeignService.findBySkuId(orderEntityVo.getSkuId());
//        ProductVo productVo = productVoResult.getData();
//        if (productVo==null){
//            return Result.error("获取商品信息失败");
//        }
        orderEntity.setSkuId(orderEntityVo.getSkuId());
        orderEntity.setSpuId(cartEntity.getSpuId());
        //获取商品库存信息
        Result<InventoryEntityVo> inventoryEntityVoResult = shopFeignService.findInventoryBySpuId(cartEntity.getSpuId());
        InventoryEntityVo inventoryEntityVo = inventoryEntityVoResult.getData();
        if (inventoryEntityVo==null){
            return Result.error("获取商品库存信息失败");
        }
        //购买商品数量小于或等于库存数 ，否则 报错
//        if (orderEntityVo.getBuyNum().intValue()>inventoryEntityVo.getInventoryCount().intValue()){
//            return  Result.error("该商品库存数量不足");
//        }
        // 修改 锁库存数量(加入购买数量)
        inventoryEntityVo.setInventoryLock(inventoryEntityVo.getInventoryLock()+orderEntityVo.getBuyNum());
        shopFeignService.updateInventory(inventoryEntityVo);
        //购买商品总价钱
//        BigDecimal totalAmount= productVo.getSkuPrice().multiply(BigDecimal.valueOf(orderEntityVo.getBuyNum()));
        orderEntity.setTotalAmount(orderEntityVo.getCartPrice());

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
        //备注
        orderEntity.setRemark(orderEntityVo.getRemark());
        //生成下单时间
        orderEntity.setCreateTime(new Date());
        //支付金额(总-优惠卷价格-积分兑换价格+运费价格)
        BigDecimal payAmount = orderEntity.getTotalAmount().subtract(orderEntity.getIntegrationAmount()).subtract(orderEntity.getCouponAmount()).add(orderEntity.getFreightAmount());
        orderEntity.setPayAmount(payAmount);
        this.baseMapper.insert(orderEntity);
        //修改购物车状态
        cartMapper.updateCartStatus(orderEntityVo.getCartId());

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
