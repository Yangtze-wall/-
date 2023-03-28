package com.retail.colonel.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.colonel.domain.Trend;
import com.retail.colonel.domain.vo.TrendCommentVo;

import java.util.List;

/**
 * <p>
 * 团长动态表 服务类
 * </p>
 *
 * @author
 * @since 2023-03-26
 */
public interface TrendService extends IService<Trend> {

    List<Trend> selectTrend(Long id);

    TrendCommentVo selectList(String id);


}
