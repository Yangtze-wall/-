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
    private Long shopId;
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
     * 状态
     */
    private Long status;
    /**
     * 秒杀库存
     */
    private Integer seckillCount;
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
     * 秒杀商品描述介绍
     */

    private String seckillIntroduce;

    /**
     * 秒杀配置id
     */
    private Integer seckillConfigId;

    /**
     * 百分比 剩余库存/秒杀库存 = 百分比
     */
    private BigDecimal percent;


}
