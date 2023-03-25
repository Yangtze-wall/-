package com.retail.user.domain;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class PowerEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 权限名称
     */
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Power{" +
        ", id=" + id +
        ", name=" + name +
        "}";
    }
}
