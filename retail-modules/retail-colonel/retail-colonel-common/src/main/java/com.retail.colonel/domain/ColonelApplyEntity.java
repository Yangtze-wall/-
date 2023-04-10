package com.retail.colonel.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
/**
 * <p>
 * 团长申请详细信息
 * </p>
 *
 * @author
 * @since 2023-04-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("retail_colonel_apply")
public class ColonelApplyEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 团长申请id
     */
    private Long id;

    /**
     * 头像
     */
    private String picture;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 身份证号
     */
    private String idcord;

    /**
     * 所属城市
     */
    private String city;

    /**
     * 自提点详细地址信息
     */
    private String addressInfo;

    /**
     * 状态(1.待审核 2. 成功 3.失败)
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 审核时间
     */
    private Date verifyTime;

    /**
     * 管理员
     */
    private Long admin;

    /**
     * 申请人
     */
    private Long userId;

    /**
     * 身份证 正面图片
     */
    private String idcordFront;

    /**
     * 身份证 反面图片
     */
    private String idcordReverse;

    /**
     * 活体检测
     */
    private String bioassay;

}
