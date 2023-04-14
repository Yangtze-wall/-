package com.retail.common.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author IntegrationHistoryEntityVo
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.common.domain.vo
 * @date: 2023-04-09  18:34
 * @Created by:  12871
 * @Description:
 * @Version:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntegrationHistoryEntityVo {
        /**
         * id
         */
        private Long id;
        /**
         * 用户id
         */
        private Long userId;
        /**
         * 创建时间
         */
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss[.fffff]")
        private Date createTime;
        /**
         * 积分数量
         */
        private Integer count;
        /**
         * 备注
         */
        private String remark;
        /**
         * 来源类型（1签到，2购买，3兑换优惠券 4.积分抵扣）
         */
        private Integer sourceType;
}

