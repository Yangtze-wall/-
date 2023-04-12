package com.retail.bargain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.retail.bargain.domain.SeckillEntity;
import com.retail.bargain.vo.*;
import com.retail.common.domain.vo.InventoryVo;
import com.retail.common.domain.vo.SeckillSpuVo;
import com.retail.common.domain.vo.SkuInventoryVo;
import com.retail.common.domain.vo.UserEntityVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SeckillMapper extends BaseMapper<SeckillEntity> {
    List<SeckillSpuVo> selectSpu();

    SkuInventoryVo selectSkuInventory(Long spuId);

    void updateEsRedis(SeckillEntity seckillEntity);


    /**
     * 查询订单
     * @param orderId
     * @return
     */
    OrderVo findByIdOrder(Long orderId);

    /**
     *
     * @param orderId
     */
    void updateOrderStatus(Long orderId);

    /**
     * 搜索订单表
     *
     * @param seckillId
     * @param userId
     * @return
     */
    List<OrderVo> findOrderBySeckillIdUserId(@Param("seckillId") Long seckillId, @Param("userId") Long userId);

    /**
     * 登录
     * @param userId
     * @return
     */
    UserEntityVo userLogin(Long userId);

    /**
     * 根据商品主键id查询商品信息
     * @param spuId
     * @return
     */
    SpuEntity findByIdSpu(@Param("spuId") Long spuId);

    /**
     * 收货地址信息
     * @param addressId
     * @return
     */
    AddressVo selectAddress(@Param("addressId") Integer addressId);

    /**
     * spuid查询库存表
     * @param spuId
     * @return
     */
    InventoryVo findByIdInventory(@Param("spuId") Long spuId);

    /**
     * 锁库存
     * @param id
     * @param count
     */
    void inventoryLock(@Param("id") Long id, @Param("count") Integer count);

    /**
     * 回滚库存表库存
     * @param id
     * @param count
     */
    void updateInventoryCount(@Param("id") Long id, @Param("count") int count);

    /**
     * 回滚库存表  锁库存 的库存
     * @param id
     * @param count
     */
    void updateInventoryLock(@Param("id") Long id, @Param("count") int count);

    /**
     * sku id 查询
     * @param skuId
     * @return
     */
    SkuEntity findByIdSku(@Param("skuId") Long skuId);

    /**
     * 支付表添加
     * @param payment
     */
    void paymentAdd(Payment payment);
}
