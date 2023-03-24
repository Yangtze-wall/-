package com.retail.auth.service;

/**
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.auth.service
 * @Classname: SmsService
 * @CreateTime: 2023-03-24  11:42
 * @Created by: 喵喵
 * @Description:
 * @Version:
 */
public interface SmsService {
    public void sendSms(String phone,String code);
}
