package com.retail.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.retail.user.domain.UserRecordEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 账户记录流水表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 09:50:51
 */
@Mapper
public interface UserRecordMapper extends BaseMapper<UserRecordEntity> {

    List<UserRecordEntity> selectCommission(UserRecordEntity userRecordEntity);

}
