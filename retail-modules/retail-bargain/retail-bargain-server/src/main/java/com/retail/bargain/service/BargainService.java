package com.retail.bargain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.bargain.domain.BargainEntity;
import com.retail.common.domain.vo.BargainEntityVo;
import com.retail.common.result.Result;

import java.util.List;
import java.util.Map;

/**
 * 砍价表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 11:22:38
 */
public interface BargainService extends IService<BargainEntity> {


    List<BargainEntity> select();

    Result bargainInsert(BargainEntity bargainEntity);

    Result bargainUpdate(BargainEntity bargainEntity);

    Result<BargainEntityVo> findByIdBargainEntity(Long id);

    Result deleteBargainlEntity(Long id);

    Result<String> findUrl(Long id);

    Result updateInsertBargain(Long id, String url);
}

