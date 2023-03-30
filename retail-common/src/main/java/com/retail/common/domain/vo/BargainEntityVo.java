package com.retail.common.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author BargainEntityVo
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.common.domain.vo
 * @date: 2023-03-29  09:35
 * @Created by:  12871
 * @Description:
 * @Version:
 */
@Data
public class BargainEntityVo {

    /**
     * 砍价表主键
     */
    private Long id;
    /**
     * 砍价活动名称
     */
    private String bargainName;
    /**
     * 砍价活动简介
     */
    private String bargainIntroductory;
    /**
     * 商品id
     */
    private Long spuId;
    /**
     * 已砍百分比
     */
    private BigDecimal bargainAccomplish;
    /**
     * 剩余百分比
     */
    private BigDecimal bargainUnfinished;
    /**
     * 需要人数
     */
    private Integer needNumberPeople;
    /**
     * 总过期时间
     */
    private Date totalExpirationTime;
    /**
     * 砍价状态
     */
    private Integer bargainStatus;
    /**
     * 成功砍价人数
     */
    private Integer bargainNumberPeople;
    /**
     * 砍价人id
     */
    private Long userId;
    /**
     * 活动状态
     */
    private Integer status;
}
