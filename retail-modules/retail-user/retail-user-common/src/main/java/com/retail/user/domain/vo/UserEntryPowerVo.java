package com.retail.user.domain.vo;

import lombok.Data;

/**
 * @author userEntryPowerVo
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.common.domain.vo
 * @date: 2023-03-24  11:35
 * @Created by:  12871
 * @Description:
 * @Version:
 */
@Data
public class UserEntryPowerVo {
    private Long id;
    private String username;
    private String password;
    private String realName;
    private Integer status;
    private String remark;
    private String powerName;

}
