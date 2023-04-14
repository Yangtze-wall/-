package com.retail.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.common.domain.vo.CartEntityVo;
import com.retail.common.result.Result;
import com.retail.order.domain.CartEntity;
import com.retail.order.domain.PayMentEntity;

import java.util.List;

/**
 * 订单详情表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 18:50:56
 */
public interface CartService extends IService<CartEntity> {


    Result insertCart(CartEntityVo cartEntityVo);


    Result<List<CartEntityVo>> getCartList();


    Result updateCartNum(CartEntity cartEntity);

    Result<CartEntityVo> findCartByCartId(Long id);

}

