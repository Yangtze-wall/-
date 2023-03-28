package com.retail.colonel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.colonel.domain.Team;
import com.retail.common.result.Result;

/**
 * <p>
 * 拼团表 服务类
 * </p>
 *
 * @author
 * @since 2023-03-27
 */
public interface TeamService extends IService<Team> {

    Result applyUser(Long id, String content);
}
