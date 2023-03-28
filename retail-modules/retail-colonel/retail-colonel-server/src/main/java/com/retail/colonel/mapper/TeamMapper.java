package com.retail.colonel.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.retail.colonel.domain.Team;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 拼团表 Mapper 接口
 * </p>
 *
 * @author
 * @since 2023-03-27
 */
@Mapper
public interface TeamMapper extends BaseMapper<Team> {

}
