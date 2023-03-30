package com.retail.bargain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.retail.bargain.domain.UrlEntry;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author UrlMapper
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.bargain.mapper
 * @date: 2023-03-27  20:08
 * @Created by:  12871
 * @Description:
 * @Version:
 */
@Mapper
public interface UrlMapper extends BaseMapper<UrlEntry> {
}
