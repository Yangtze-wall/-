package com.retail.shop.domain.vo;

import lombok.Data;

import java.util.List;


@Data
public class SkuAttrVo {
    private Long attrId;
    private String attrName;
    private String attrValues;
    private List<String> attrValuesList;
}
