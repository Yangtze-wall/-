package com.retail.order.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.retail.order.domain.PaymentEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付信息表
 *
 * @author fengge
 * @email fengge@atguigu.com
 * @date 2022-03-21 19:10:45
 */
@Mapper
public interface PaymentMapper extends BaseMapper<PaymentEntity> {

}
