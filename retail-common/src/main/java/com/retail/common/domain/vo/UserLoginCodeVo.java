package com.retail.common.domain.vo;

import lombok.Data;

/**
 * @author UserLoginPasswordVo
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.common.domain.vo
 * @date: 2023-03-24  09:34
 * @Created by:  12871
 * @Description:
 * @Version:
 */
@Data
public class UserLoginCodeVo {
    /**
     * 手机号
     */
    private String phone;

    /**
     * 验证码
     */
    private String code;
}
