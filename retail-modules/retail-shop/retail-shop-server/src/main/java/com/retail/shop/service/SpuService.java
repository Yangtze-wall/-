package com.retail.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.common.domain.vo.SpuVo;
import com.retail.shop.domain.SpuEntity;

import java.util.List;

/**
 * 商品表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 17:00:28
 */
public interface SpuService extends IService<SpuEntity> {

    List<SpuEntity> selectListSpu();

    SpuVo selectSpu(Long spuId);
}

