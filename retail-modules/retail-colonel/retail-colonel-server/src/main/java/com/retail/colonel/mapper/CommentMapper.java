package com.retail.colonel.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.retail.colonel.domain.Comment;
import com.retail.colonel.domain.vo.CommentVo;
import com.retail.colonel.domain.vo.SelectVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 团长动态评论表	 Mapper 接口
 * </p>
 *
 * @author
 * @since 2023-03-26
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    List<Comment> selectshowList();

    List<CommentVo> findByTrendId( SelectVo selectVo);
}
