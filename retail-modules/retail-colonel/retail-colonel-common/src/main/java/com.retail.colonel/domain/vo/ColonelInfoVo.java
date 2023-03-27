package com.retail.colonel.domain.vo;

import com.retail.colonel.domain.ColonelEntity;
import com.retail.common.domain.vo.UserEntityVo;
import lombok.Data;

/**
 * ClassName ColonelInfoVO
 * Date 2023/3/26 10:40
 **/
@Data
public class ColonelInfoVo {
    private UserEntityVo user;
    private ColonelEntity colonel;
}
