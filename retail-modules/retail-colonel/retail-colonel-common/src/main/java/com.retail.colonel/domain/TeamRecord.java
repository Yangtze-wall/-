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
 * 拼团记录表
 * </p>
 *
 * @author
 * @since 2023-03-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("retail_team_record")
public class TeamRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 拼团记录表主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 拼团表id
     */
    private Long teamId;

    /**
     * 拼团人id
     */
    private Long userId;

    /**
     * 拼团时间
     */
    private Date teamOrderTime;

    /**
     * 状态
     */
    private Integer status;

}
