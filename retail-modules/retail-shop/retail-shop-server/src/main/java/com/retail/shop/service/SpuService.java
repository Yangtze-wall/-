package com.retail.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.common.result.Result;
import com.retail.shop.domain.SpuEntity;
import com.retail.shop.domain.vo.SearchParam;
import com.retail.shop.domain.vo.SkuModelVo;

import javax.naming.directory.SearchResult;
import java.util.List;

/**
 * 商品表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 17:00:28
 */
public interface SpuService extends IService<SpuEntity> {


    Result insertSpu(SpuEntity spuEntity);

    Result deleteSpuById(Long id);

    Result updateSpu(SpuEntity spuEntity);



}

