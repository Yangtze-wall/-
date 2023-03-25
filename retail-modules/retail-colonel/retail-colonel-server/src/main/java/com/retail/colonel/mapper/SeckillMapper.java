package com.retail.colonel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.retail.colonel.domain.SeckillEntity;
import com.retail.common.domain.vo.SeckillSpuVo;
import com.retail.common.domain.vo.SkuInventoryVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SeckillMapper extends BaseMapper<SeckillEntity> {
    List<SeckillSpuVo> selectSpu();

    SkuInventoryVo selectSkuInventory(Long spuId);

    void updateEsRedis(SeckillEntity seckillEntity);

}
