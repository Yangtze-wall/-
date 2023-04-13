package com.retail.bargain.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ProjectName: retail-cloud
 * @Package: com.retail.colonel.domain
 * @ClassName: Seckill
 * @Author: 2766395184
 * @Description: 秒杀表
 * @Date: 2023/3/25 9:53
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retail_seckill")
public class SeckillEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long skuId;

    private String slideShow;

    private String seckillHeadline;
    private BigDecimal seckillPrice;

    private Date seckillStartTime;

    private Date seckillEndTime;

    private Date seckillCreateTime;

    private Integer postFree;

    private Integer seckillLimit;

    private Integer seckillResidueCount;

    private Integer esRedis;


}
