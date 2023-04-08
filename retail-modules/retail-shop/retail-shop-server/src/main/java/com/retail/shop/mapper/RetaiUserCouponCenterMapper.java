package com.retail.shop.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.retail.shop.domain.RetaiUserCouponCenterEntity;
import com.retail.shop.domain.vo.RetaiUserCouponCenterEntityVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户/优惠卷中间表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 09:56:18
 */
@Mapper
public interface RetaiUserCouponCenterMapper extends BaseMapper<RetaiUserCouponCenterEntity> {


    List<RetaiUserCouponCenterEntityVo> selectStoreCouponByUserId(@Param("id") Long id);
}
