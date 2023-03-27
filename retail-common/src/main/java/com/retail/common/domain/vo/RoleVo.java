package com.retail.common.domain.vo;

import lombok.Data;

/**
 * @ProjectName:    retail-cloud
 * @Package:        com.retail.common.domain.vo
 * @ClassName:      RoleVo
 * @Author:     2766395184
 * @Description:
 * @Date:    2023/3/26 22:23
 * @Version:    1.0
 */
@Data
public class RoleVo {


    private Long id;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色描述
     */
    private String roleRemake;

}
