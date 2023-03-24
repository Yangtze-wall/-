package com.retail.auth.vo;

import lombok.Data;

/**
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.auth.vo
 * @Classname: SmsParamVo
 * @CreateTime: 2023-03-24  19:44
 * @Created by: 喵喵
 * @Description:
 * @Version:
 */
@Data
public class SmsParamVo {

    /**
     * 设置id 唯一性
     */
    private String msgId;
    /**
     *手机号
     */
    private String phone;
    /**
     *验证码
     */
    private String code;




}
