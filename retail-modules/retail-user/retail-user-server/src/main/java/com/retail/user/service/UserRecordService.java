package com.retail.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.common.domain.vo.UserRecordEntityVo;
import com.retail.common.result.Result;
import com.retail.user.domain.UserRecordEntity;


import java.util.Map;

/**
 * 账户记录流水表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 09:50:51
 */
public interface UserRecordService extends IService<UserRecordEntity> {


    Result userRecordInsert(UserRecordEntityVo userRecordEntityVo);
}

