package com.retail.colonel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.colonel.domain.ColonelApplyEntity;
import com.retail.common.domain.vo.CommissionVo;
import com.retail.common.result.Result;


import java.util.List;
import java.util.Map;

/**
 * 团长申请表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 10:09:39
 */
public interface ColonelApplyService extends IService<ColonelApplyEntity> {


    Result colonelApplyService(ColonelApplyEntity colonelApplyEntity);

    List<CommissionVo> selectCommission();

}

