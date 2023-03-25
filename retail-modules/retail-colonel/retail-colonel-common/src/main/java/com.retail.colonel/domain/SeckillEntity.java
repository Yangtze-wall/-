package com.retail.colonel.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ProjectName:    retail-cloud
 * @Package:        com.retail.colonel.domain
 * @ClassName:      Seckill
 * @Author:     2766395184
 * @Description:  秒杀表
 * @Date:    2023/3/25 9:53
 * @Version:    1.0
 */
@Data
public class SeckillEntity {

    private Long id	;

    private Long shopId	;

    private Integer slideShow	;

    private Integer seckillHeadline	;

    private BigDecimal seckillPrice	;

    private Date seckillStartTime	;

    private Date seckillEndTime	;

    private Date seckillCreateTime;

    private Integer status	;

    private Integer seckillCount	;

    private Integer seckillResidueCount	;

    private Integer esRedis;





}
