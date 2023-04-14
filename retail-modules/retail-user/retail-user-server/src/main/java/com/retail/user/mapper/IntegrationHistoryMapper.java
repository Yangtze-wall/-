package com.retail.user.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.retail.user.domain.IntegrationHistoryEntity;
import com.retail.user.domain.vo.SignTimeSearchVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 购物积分记录表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 09:50:51
 */
@Mapper
public interface IntegrationHistoryMapper extends BaseMapper<IntegrationHistoryEntity> {


    List<IntegrationHistoryEntity> selectSign(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("userId") Long userId);

}
