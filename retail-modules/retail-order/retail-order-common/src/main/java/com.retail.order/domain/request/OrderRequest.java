package com.retail.order.domain.request;

import lombok.Data;

/**
 * @author Lenovo
 * @Package_name com.retail.order.domain.request
 * @Description TODO
 * @createTime 2023/4/3 9:31
 */
@Data
public class OrderRequest {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 商品名称
     */
    private Long spuName;
    /**
     * 商品简介
     */
    private String spuDesc;


    /**
     * 分页
     */
    private Integer pageNum=1;
    private Integer pageSize=3;
}
