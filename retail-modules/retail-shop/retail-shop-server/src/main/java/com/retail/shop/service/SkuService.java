package com.retail.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.common.domain.vo.SkuEntityVo;
import com.retail.common.result.Result;
import com.retail.shop.domain.SkuEntity;

/**
 * 商品 sku 表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 17:00:28
 */
public interface SkuService extends IService<SkuEntity> {

    Result<SkuEntityVo> findBySkuEntry(Long spuId);

    Result updateSkuSell(SkuEntityVo skuEntityVo);
}

