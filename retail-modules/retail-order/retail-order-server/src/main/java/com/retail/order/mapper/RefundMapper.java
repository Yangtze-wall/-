package com.retail.order.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.retail.order.domain.RefundEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 退款信息
 *
 * @author fengge
 * @email fengge@atguigu.com
 * @date 2022-03-21 19:10:44
 */
@Mapper
public interface RefundMapper extends BaseMapper<RefundEntity> {

}
