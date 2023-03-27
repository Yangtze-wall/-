package com.retail.colonel.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.retail.colonel.domain.Comment;
import com.retail.colonel.mapper.CommentMapper;
import com.retail.colonel.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 团长动态评论表	 服务实现类
 * </p>
 *
 * @author
 * @since 2023-03-26
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> show() {
        List<Comment> comments = commentMapper.selectshowList();
        return comments;
    }
}
