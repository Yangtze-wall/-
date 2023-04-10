package com.retail.bargain.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Lenovo
 * @Package_name com.retail.bargain.vo
 * @Description TODO
 * @createTime 2023/3/24 17:14
 */
@Data
public class SeckillEntityVo {
    private Long id;
    /**
     * 商品id
     */
    private Long spuId;
    /**
     * 轮播图
     */
    private String slideshow;
    /**
     * 秒杀商品标题
     */
    private String seckillHeadline;
    /**
     * 秒杀价
     */
    private BigDecimal seckillPrice;
    /**
     * 秒杀开始时间
     */
    private Date seckillStartTime;
    /**
     * 秒杀结束时间
     */
    private Date seckillEndTime;
    /**
     * 添加时间
     */
    private Date seckillCreateTime;
    /**
     * 剩余库存
     */
    private Integer seckillResidueCount;
    /**
     * 每人限购次数
     */
    private Integer seckillLimit;
    /**
     * 是否包邮
     */
    private Integer postFree;



    /**
     * 商品名称
     */
    private String spuName;
}
