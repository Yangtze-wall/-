package com.retail.bargain.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author Lenovo
 * @Package_name com.retail.bargain.vo
 * @Description TODO
 * @createTime 2023/4/6 20:24
 */
@Data
public class AddressVo {
    /**
     * id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 收货人
     */
    private String name;
    /**
     * 电话
     */
    private String phone;
    /**
     * 邮编
     */
    private String postCode;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 区
     */
    private String region;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 是否默认地址
     */
    private Integer defaultStatus;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
