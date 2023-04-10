package com.retail.colonel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * <p>
 * 团长表
 * </p>
 *
 * @author
 * @since 2023-04-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("retail_colonel")
public class ColonelEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 团长id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 等级
     */
    private Long gradeId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证
     */
    private String cord;

    /**
     * 当月佣金
     */
    private BigDecimal monthCommission;

    /**
     * 总共佣金
     */
    private BigDecimal totalCommission;

    /**
     * 已提现佣金
     */
    private BigDecimal withdraw;

    /**
     * 剩余佣金
     */
    private BigDecimal surplusCommission;

    /**
     * 团长详细地址(冗余字段)
     */
    private String address;

    /**
     * 0未申请 1等待审核 2拒绝 3同意 4停用
     */
    private Integer status;

}
