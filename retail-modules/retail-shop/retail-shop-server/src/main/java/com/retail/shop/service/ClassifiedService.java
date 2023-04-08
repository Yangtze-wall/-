package com.retail.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.shop.domain.ClassifiedEntity;
import com.retail.shop.domain.vo.ClassifiedVo;

import java.util.List;

/**
 * 商品分类表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 17:00:28
 */
public interface ClassifiedService extends IService<ClassifiedEntity> {


    List<ClassifiedVo> selectClassifiedTreeList();
}

