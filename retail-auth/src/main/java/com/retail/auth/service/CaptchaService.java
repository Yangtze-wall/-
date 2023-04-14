package com.retail.auth.service;

import com.retail.auth.vo.Captcha;

/**
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.auth.service
 * @Classname: CaptchaService
 * @CreateTime: 2023-03-27  18:48
 * @Created by: 喵喵
 * @Description:
 * @Version:
 */
public interface CaptchaService {
    Captcha getCaptcha(Captcha captcha);
    String checkImageCode(String nonceStr, String value);

}
