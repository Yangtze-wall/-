package com.retail.shop.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.retail.common.domain.vo.InventoryVo;
import com.retail.shop.domain.InventoryEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 库存表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 09:56:18
 */
@Mapper
public interface InventoryMapper extends BaseMapper<InventoryEntity> {

    InventoryVo selectInventory(Long spuId);

}
