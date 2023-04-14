package com.retail.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.vo.CartEntityVo;
import com.retail.common.domain.vo.ProductVo;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.result.Result;
import com.retail.common.utils.JwtUtils;
import com.retail.order.domain.CartEntity;
import com.retail.order.feign.ShopFeignService;
import com.retail.order.mapper.CartMapper;
import com.retail.order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Service("cartService")
public class CartServiceImpl extends ServiceImpl<CartMapper, CartEntity> implements CartService {


    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private ShopFeignService shopFeignService;

    public UserEntityVo userInfo(){
        String token = request.getHeader("token");
        String userKey = JwtUtils.getUserKey(token);
        String s = redisTemplate.opsForValue().get(TokenConstants.LOGIN_TOKEN_KEY + userKey);
        UserEntityVo user = JSON.parseObject(s, UserEntityVo.class);
        return user;
    }

    @Override
    public Result insertCart(CartEntityVo cartEntityVo) {
        CartEntity cartEntity = new CartEntity();
        cartEntity.setUserId(userInfo().getId());

        cartEntity.setCreateTime(new Date());
        cartEntity.setSkuId(cartEntityVo.getSkuId());

        // skuId 获取 sku 信息
        Result<ProductVo> productVoResult = shopFeignService.findBySkuId(cartEntityVo.getSkuId());
        ProductVo productVo = productVoResult.getData();
        if (productVo==null){
            return Result.error("获取商品信息失败");
        }
        //购买商品数小于商品库存数
        if (cartEntityVo.getBuyNum().intValue()> productVo.getInventoryCount().intValue()){
            return Result.error("商品库存不足");
        }
        cartEntity.setBuyNum(cartEntityVo.getBuyNum());
        //价格
        BigDecimal cartPrice= productVo.getSkuPrice().multiply(BigDecimal.valueOf(cartEntityVo.getBuyNum()));
        cartEntity.setCartPrice(cartPrice);
        cartEntity.setSpuId(productVo.getSpuId());
        cartEntity.setCartStatus(1);

        this.baseMapper.insert(cartEntity);
        return Result.success();
    }

    /**
     * 购物车列表
     * @return
     */
    @Override
    public Result<List<CartEntityVo>> getCartList() {
        List<CartEntityVo> cartEntityVoList= cartMapper.getCartList(userInfo().getId());
        return Result.success(cartEntityVoList);
    }

    /**
     * 修改购买商品数量
     */
    @Override
    public Result updateCartNum(CartEntity cartEntity) {
        this.baseMapper.update(cartEntity,new UpdateWrapper<CartEntity>().lambda().eq(CartEntity::getBuyNum,cartEntity.getBuyNum()));
        return Result.success();
    }

    @Override
    public Result<CartEntityVo> findCartByCartId(Long id) {
        CartEntityVo cartEntityVo = cartMapper.findCartByCartId(id);
        return Result.success(cartEntityVo);
    }

}
