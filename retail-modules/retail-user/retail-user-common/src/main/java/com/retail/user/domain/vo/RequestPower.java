package com.retail.user.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * ClassName RequestPower
 * Date 2023/3/23 20:09
 **/
@Data
public class RequestPower {
    private String realName;
    private Integer pageNum=1;
    private Integer pageSize =3;

}
