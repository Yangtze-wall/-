package com.retail.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.user.domain
 * @Classname: SignEntity
 * @CreateTime: 2023-04-03  09:49
 * @Created by: 喵喵
 * @Description: 签到天数表
 * @Version:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retail_sign")
public class SignEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 连续签到天数
     */
    private Integer signTime;
    /**
     * 签到积分
     */
    private Integer signIntegral;






}
