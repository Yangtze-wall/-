package com.retail.order.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.retail.common.domain.vo.CartEntityVo;
import com.retail.order.domain.CartEntity;
import com.retail.order.domain.PayMentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 购物车表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 18:50:56
 */
@Mapper
public interface CartMapper extends BaseMapper<CartEntity> {


    List<CartEntityVo> getCartList(@Param("id") Long id);

    CartEntityVo findCartByCartId(@Param("id") Long id);

    void updateCartStatus(@Param("cartId") Long cartId);

}
