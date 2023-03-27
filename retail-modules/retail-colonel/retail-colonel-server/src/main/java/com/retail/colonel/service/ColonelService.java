package com.retail.colonel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.colonel.domain.ColonelEntity;

/**
 * 团长表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 10:09:39
 */
public interface ColonelService extends IService<ColonelEntity> {


    ColonelEntity findById(Long id);

    ColonelEntity getInfo(Long id);
}

