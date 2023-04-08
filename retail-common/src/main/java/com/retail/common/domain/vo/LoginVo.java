package com.retail.common.domain.vo;

import lombok.Data;

/**
 * ClassName LoginVo
 * Date 2023/3/26 19:35
 **/
@Data
public class LoginVo {
    private String nonceStr;
    private String value;
    private String phone;
    private String password;
}
