package com.retail.shop.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.retail.common.domain.vo.SkuEntityVo;
import com.retail.common.result.Result;
import com.retail.shop.domain.SkuEntity;
import com.retail.shop.mapper.SkuMapper;
import com.retail.shop.service.SkuService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



@Service("skuService")
public class SkuServiceImpl extends ServiceImpl<SkuMapper, SkuEntity> implements SkuService {

    @Override
    public Result<SkuEntityVo> findBySkuEntry(Long spuId) {
        SkuEntity skuEntity=baseMapper.selectOne(new QueryWrapper<SkuEntity>().lambda().eq(SkuEntity::getSpuId,spuId));
        SkuEntityVo skuEntityVo = new SkuEntityVo();
        BeanUtil.copyProperties(skuEntity,skuEntityVo);
        return Result.success(skuEntityVo);
    }

    @Override
    public Result updateSkuSell(SkuEntityVo skuEntityVo) {
        SkuEntity skuEntity = new SkuEntity();
        BeanUtil.copyProperties(skuEntityVo,skuEntity);
        baseMapper.updateById(skuEntity);
        return Result.success("修改销量成功");
    }
}
