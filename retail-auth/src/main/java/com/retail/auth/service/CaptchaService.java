package com.retail.auth.service;

import com.retail.auth.domain.Captcha;

public interface CaptchaService {
    Captcha getCaptcha(Captcha captcha);

    String checkImageCode(String nonceStr, String value);
}
