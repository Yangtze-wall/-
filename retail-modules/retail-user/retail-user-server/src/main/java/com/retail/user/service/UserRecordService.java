package com.retail.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.common.domain.vo.CommissionVo;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.user.domain.UserRecordEntity;


import java.util.List;
import java.util.Map;

/**
 * 账户记录流水表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 09:50:51
 */
public interface UserRecordService extends IService<UserRecordEntity> {


    List<CommissionVo> selectCommission(UserEntityVo userEntityVo);
}

