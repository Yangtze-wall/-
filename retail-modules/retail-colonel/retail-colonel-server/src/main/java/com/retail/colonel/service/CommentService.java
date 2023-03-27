package com.retail.colonel.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.colonel.domain.Comment;

import java.util.List;

/**
 * <p>
 * 团长动态评论表	 服务类
 * </p>
 *
 * @author
 * @since 2023-03-26
 */
public interface CommentService extends IService<Comment> {

    List<Comment> show();


}
