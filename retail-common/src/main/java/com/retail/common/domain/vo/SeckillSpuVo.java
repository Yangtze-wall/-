package com.retail.common.domain.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ProjectName:    retail-cloud
 * @Package:        com.retail.common.domain.vo
 * @ClassName:      SeckillSpuVo
 * @Author:     2766395184
 * @Description:
 * @Date:    2023/3/25 10:46
 * @Version:    1.0
 */
@Data
public class SeckillSpuVo {

    private Long id	;

    private Long spuId	;

    private Integer slideShow	;

    private String seckillHeadline	;

    private BigDecimal seckillPrice	;

    private Date seckillStartTime	;

    private Date seckillEndTime	;

    private Date seckillCreateTime;

    private Integer status	;

    private Integer seckillCount	;

    private Integer seckillResidueCount	;

    private Integer esRedis;




    private Long skuId;
    /**
     * 商品名称
     */
    private String spuName;
    /**
     * 商品介绍
     */
    private String spuLetter;
    /**
     * 商家id
     */
    private Integer shopId;
    /**
     * 商品上架时间
     */
    private Date spuCreatetime;
    /**
     * 商品修改时间
     */
    private Date spuUpdatetime;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 1 上架 2 下架
     */
    private Integer spuStatus;
    /**
     * 1 同步 2 未同步
     */
    private Integer esStatus;











}
