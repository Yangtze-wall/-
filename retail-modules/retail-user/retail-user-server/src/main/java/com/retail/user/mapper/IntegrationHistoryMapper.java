package com.retail.user.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.retail.user.domain.IntegrationHistoryEntity;
import com.retail.user.domain.request.UserIntegralAdd;
import org.apache.ibatis.annotations.Mapper;

/**
 * 购物积分记录表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 09:50:51
 */
@Mapper
public interface IntegrationHistoryMapper extends BaseMapper<IntegrationHistoryEntity> {
    /**
     * 用户积分添加
     * @param userIntegralAdd
     * @return
     */
    int IntergralAdd(UserIntegralAdd userIntegralAdd);
}
