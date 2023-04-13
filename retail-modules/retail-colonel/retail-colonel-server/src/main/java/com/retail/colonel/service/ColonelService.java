package com.retail.colonel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.colonel.domain.ColonelEntity;
import com.retail.common.domain.vo.UserEntityVo;


import java.util.List;
import java.util.Map;

/**
 * 团长表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 10:09:39
 */
public interface ColonelService extends IService<ColonelEntity> {


    List<ColonelEntity> selectColonel(UserEntityVo userEntityVo);

    ColonelEntity findbyColonelId(Long userId);
}

