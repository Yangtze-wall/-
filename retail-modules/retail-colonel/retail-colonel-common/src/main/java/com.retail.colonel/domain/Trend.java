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
 * 团长动态表
 * </p>
 *
 * @author
 * @since 2023-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("retail_trend")
public class Trend implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 社区活动
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 团长id
     */
    private Long colonelId;

    /**
     * 内容
     */
    private String content;

    /**
     * 图片
     */
    private String images;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 浏览量
     */
    private Long views;

    /**
     * 点赞量
     */
    private Long likes;

}
