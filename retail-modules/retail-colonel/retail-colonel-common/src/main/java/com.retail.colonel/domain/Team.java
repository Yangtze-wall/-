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
 * 拼团表
 * </p>
 *
 * @author
 * @since 2023-03-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("retail_team")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 拼团表主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

 private Long skuId;

    /**
     * 所需人数
     */
    private Integer needNumberPeople;

    /**
     * 已参团人数
     */
    private Integer teamNumberPeople;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 团长id
     */
    private Long colonelId;

    /**
     * 发起拼团时间
     */
    private Date teamStartTime;

    /**
     * 拼团结束时间
     */
    private Date teamEndTime;
    /**
     * 优惠金额
     */
    private BigDecimal discountPrice;


}
