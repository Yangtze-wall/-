package com.retail.shop.service.impl;

import cn.hutool.core.bean.BeanUtil;
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
    public Result<InventoryEntityVo> findByInventoryEntity(Long spuId) {
        InventoryEntityVo inventoryEntityVo = new InventoryEntityVo();
        InventoryEntity inventoryEntity = baseMapper.selectOne(new QueryWrapper<InventoryEntity>().lambda().eq(InventoryEntity::getSpuId, spuId));
        BeanUtil.copyProperties(inventoryEntity,inventoryEntityVo);
        return Result.success(inventoryEntityVo);
    }

    @Override
    public Result updateInventoryLock(InventoryEntityVo inventoryEntityVo) {
        InventoryEntity inventoryEntity = new InventoryEntity();
        BeanUtil.copyProperties(inventoryEntityVo,inventoryEntity);
        baseMapper.update(inventoryEntity,new QueryWrapper<InventoryEntity>().lambda().eq(InventoryEntity::getSpuId,inventoryEntityVo.getSpuId()));
        return Result.success("锁定库存成功");
    }
}
