package com.retail.user.mapper;

import com.retail.user.domain.vo.UserEntryPowerVo;
import com.retail.user.domain.PowerUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 权限  用户 中间表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 19:31:59
 */
@Mapper
public interface PowerUserMapper extends BaseMapper<PowerUserEntity> {

    List<UserEntryPowerVo> list();
}
