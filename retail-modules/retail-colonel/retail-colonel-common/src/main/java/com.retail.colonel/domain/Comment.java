package com.retail.colonel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
/**
 * <p>
 * 团长动态评论表
 * </p>
 *
 * @author
 * @since 2023-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("retail_comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论id
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 评论人
     */
    private Long userId;

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

}
