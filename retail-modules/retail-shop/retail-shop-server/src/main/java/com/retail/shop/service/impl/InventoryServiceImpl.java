package com.retail.shop.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.retail.common.domain.vo.InventoryEntityVo;
import com.retail.common.result.Result;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.retail.shop.mapper.InventoryMapper;
import com.retail.shop.domain.InventoryEntity;
import com.retail.shop.service.InventoryService;


@Service("inventoryService")
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, InventoryEntity> implements InventoryService {


    @Override
    public InventoryEntityVo findInventoryBySpuId(Long spuId) {
        InventoryEntity inventoryEntity = this.baseMapper.selectOne(new QueryWrapper<InventoryEntity>().lambda().eq(InventoryEntity::getSpuId, spuId));
        InventoryEntityVo inventoryEntityVo = new InventoryEntityVo();
        BeanUtil.copyProperties(inventoryEntity,inventoryEntityVo);
        return inventoryEntityVo;
    }

    @Override
    public Result updateInventory(InventoryEntityVo inventoryEntityVo) {
        InventoryEntity inventoryEntity = new InventoryEntity();
        BeanUtil.copyProperties(inventoryEntityVo,inventoryEntity);
        this.baseMapper.update(inventoryEntity,new UpdateWrapper<InventoryEntity>().lambda().eq(InventoryEntity::getId,inventoryEntity.getId()));
        return Result.success();
    }
}
