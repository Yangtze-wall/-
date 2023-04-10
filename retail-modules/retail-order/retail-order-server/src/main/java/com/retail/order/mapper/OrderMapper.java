package com.retail.order.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.retail.order.domain.OrderEntity;
import com.retail.order.vo.SkuEntity;
import com.retail.order.vo.SpuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 订单表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 18:50:55
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderEntity> {
    /**
     * spu id 查询
     * @param spuId
     * @return
     */
    SpuEntity selectFindByIdSpu(@Param("spuId") Long spuId);

    /**
     * sku id 查询
     * @param skuId
     * @return
     */
    SkuEntity selectFindByIdSku(@Param("skuId") Long skuId);
}
