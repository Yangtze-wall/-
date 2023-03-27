package com.retail.colonel.domain.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * ClassName CommentVo
 * Date 2023/3/26 22:03
 **/
@Data
public class CommentVo  {
    private Long userId;
    private String nickName;
    private String pic;
    private Long id;

    /**
     * 动态编号
     */
    private Long trendId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 发表时间
     */
    private Date createTime;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 点赞量
     */
    private Integer likes;
    private List<CommentVo> children;
}
