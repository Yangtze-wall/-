package com.retail.colonel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * <p>
 * 团购配置表
 * </p>
 *
 * @author
 * @since 2023-03-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("retail_group_config")
public class GroupConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 是否开启社区团购（0.开启， 1.关闭
     */
    private Integer isStatus;

    /**
     * 标题
     */
    private String title;

    /**
     * 团长申请海报
     */
    private String applyBill;

    /**
     * 修饰海报
     */
    private String decorateBill;

    /**
     * 供应商申请海报
     */
    private String supplierBill;

    /**
     * 发货方式（1.快递， 2.自提， 3.团长配送）
     */
    private Integer deliveryMethod;

    /**
     * 自定义团长名称
     */
    private String customName;

    /**
     * 团长覆盖范围
     */
    private String scope;

    /**
     * 提现手续费
     */
    private BigDecimal serviceCharge;

    /**
     * 最低提现金额
     */
    private BigDecimal lowestAmount;

    /**
     * 团长佣金
     */
    private BigDecimal commission;

}
