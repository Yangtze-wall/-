package com.retail.order.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.order.domain
 * @Classname: CartEntity
 * @CreateTime: 2023-04-10  21:29
 * @Created by: 喵喵
 * @Description:
 * @Version:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retail_cart")
public class CartEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *购物车id
     */
    @TableId(type = IdType.AUTO)
    private Long  id;
    /**
     * 购买数量
     */
    private Integer  buyNum;
    /**
     *sku_id
     */
    private Long  skuId;
    /**
     *用户表id
     */
    private Long  userId;
    /**
     *spuid
     */
    private Long  spuId;
    /**
     *创建时间
     */
    private Date createTime;

    /**
     *价格
     */
    private BigDecimal cartPrice;

    /**
     * 状态（1 购物车中，2 已经下订单）
     */
    private Integer cartStatus;




}
