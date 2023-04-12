package com.retail.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.shop.domain.SkuEntity;
import com.retail.shop.domain.request.GoodsRequest;
import com.retail.shop.vo.GoodsVo;

import java.util.List;

/**
 * 商品 sku 表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 17:00:28
 */
public interface SkuService extends IService<SkuEntity> {
    /**
     * 商品列表
     * @param request
     * @return
     */
    List<GoodsVo> goodsList(GoodsRequest request);
}

