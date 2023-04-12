package com.retail.shop.domain.request;

import lombok.Data;

/**
 * @author Lenovo
 * @Package_name com.retail.shop.domain.request
 * @Description TODO
 * @createTime 2023/4/11 11:32
 */
@Data
public class GoodsRequest {
    /**
     * 商品标题
     */
    private String skuTitle;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 分页
     */
    private Integer pageNum=1;
    private Integer pageSize=3;

}
