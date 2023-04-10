package com.retail.colonel.domain.result;

import com.retail.colonel.domain.Info;
import lombok.Data;

/**
 * ClassName R
 * Date 2023/3/31 11:25
 **/
@Data
public class R {
    private Info data;
    private Integer code;
    private String msg;
    private String taskNo;
}
