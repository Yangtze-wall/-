package com.retail.bargain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Lenovo
 * @Package_name com.retail.bargain.vo
 * @Description TODO
 * @createTime 2023/4/9 19:08
 */
@Data
public class SkuEntity {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 商品图片
     */
    private String skuImage;
    /**
     * 商品标题
     */
    private String skuTitle;
    /**
     * 副标题
     */
    private String skuSubhead;
    /**
     * 销量
     */
    private Integer skuSell;
    /**
     * 库存
     */
    private Integer skuStock;
    /**
     * 价格
     */
    private BigDecimal skuPrice;
    /**
     * spuid
     */
    private Long spuId;
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 分类id
     */
    private Long classifiedId;
}
