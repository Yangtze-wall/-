package com.retail.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ClassName ApiData
 * Date 2023/3/30 18:57
 **/
@Component
@Data
@ConfigurationProperties(prefix = "alipay")
public class ApiData {
    private  String   url;
    private  String   appid;
    private  String   privateKey;
    private  String   publicKey;
    private  String   notifyUrl;
    private  String   returnUrl;
}
