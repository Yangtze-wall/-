package com.retail.shop.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.retail.shop.domain.ClassifiedEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.shop.domain.vo
 * @Classname: ClassifiedEntityVo
 * @CreateTime: 2023-03-26  10:42
 * @Created by: 喵喵
 * @Description:
 * @Version:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassifiedVo {
    private static final long serialVersionUID = 1L;

    /**
     *分类id
     */
    private Long id;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 父分类id
     */
    private Long parentId;
    /**
     * 是否显示[0-不显示，1显示]
     */
    private Integer status;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 图标地址
     */
    private String icon;
    /**
     * 计量单位
     */
    private String unit;

    /**
     * 忽略数据的映射 子集
     */
    private List<ClassifiedVo> childList;
}
