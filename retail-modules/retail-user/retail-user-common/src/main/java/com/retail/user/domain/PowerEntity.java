package com.retail.user.domain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.IdType;import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
/**
 * <p>
 * 权限表
 * </p>
 *
 * @author retail
 * @since 2023-03-23
 */
@TableName("retail_power")
@Data
public class PowerEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 权限名称
     */
    private String name;


    @Override
    public String toString() {
        return "Power{" +
        ", id=" + id +
        ", name=" + name +
        "}";
    }
}
