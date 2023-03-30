package com.retail.auth.service;

/**
 * @author SmsService
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.auth.service
 * @date: 2023-03-27  16:10
 * @Created by:  12871
 * @Description:
 * @Version:
 */
public interface SmsService {
    public void sendSms(String phone,String code);
}
