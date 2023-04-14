package com.retail.order.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.retail.order.domain.PayMentEntity;
import com.retail.order.domain.ReatilOrderInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单详情表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 18:50:56
 */
@Mapper
public interface PaymentMapper extends BaseMapper<PayMentEntity> {

}
