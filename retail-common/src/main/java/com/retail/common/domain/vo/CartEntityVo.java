package com.retail.common.domain.vo;

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
public class CartEntityVo implements Serializable {
    /**
     *购物车id
     */
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
     *购物价格
     */
    private BigDecimal cartPrice;

    /**
     * 商品图片
     */
    private String skuImage;

    /**
     * 商品标题
     */
    private String skuTitle;

    /**
     * 价格
     */
    private BigDecimal skuPrice;
    /**
     * 状态（1 购物车中，2 已经下订单）
     */
    private Integer cartStatus;







}
