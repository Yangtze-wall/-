package com.retail.shop.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.retail.shop.domain.StoreCouponEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 优惠券表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 09:56:18
 */
@Mapper
public interface StoreCouponMapper extends BaseMapper<StoreCouponEntity> {


    List<StoreCouponEntity> storeCouponList(@Param("id") Long id);

}
