package com.retail.shop.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @BelongsProject: gmall
 * @BelongsPackage: com.atguigu.gulimall.search.vo
 * @Classname: SearchParam
 * @CreateTime: 2023-02-24  19:42
 * @Created by: 喵喵
 * @Description:
 * @Version:
 */
@Data
public class SearchParam {
    /**
     * 分页
     */
    private Integer pageNum=1;
    private Integer pageSize=5;
    /**
     * 商品标题
     */
    private String skuTitle;
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 三级分类id
     */
    private Long  classifiedId;//分类查询


}
