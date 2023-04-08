package com.retail.user.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.user.domain.vo
 * @Classname: SignTimeSearchVo
 * @CreateTime: 2023-04-02  18:53
 * @Created by: 喵喵
 * @Description:
 * @Version: 签到区间查询
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignTimeSearchVo {

    private Long userId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;


}
