package com.retail.bargain.domain;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 * <p>
 * 秒杀配置表
 * </p>
 *
 * @author
 * @since 2023-03-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retail_seckill_config")
public class SeckillConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 秒杀配置主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 秒杀配置名称
     */
    private String seckillConfigName;

    /**
     * 秒杀开始时间
     */
    private Date seckillConfigStartTime;

    /**
     * 秒杀结束时间
     */
    private Date seckillConfigEndTime;

    /**
     * 秒杀轮播图
     */
    private String seckillConfigSlideshow;

    /**
     * 秒杀配置创建时间
     */
    private Date seckillConfigCreateTime;

    /**
     * 状态
     */
    private Integer status;
}
