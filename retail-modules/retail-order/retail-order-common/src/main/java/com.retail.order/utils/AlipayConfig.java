package com.retail.order.utils;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 配置文件读取
 *
 * @author 小道仙
 * @date 2020年2月18日
 */
@Configuration
@Data
@Component
public class AlipayConfig {

    /**
     * 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
     */
    private String appId = "2021000122670931";

    /**
     * 商户私钥，您的PKCS8格式RSA2私钥
     */
    private String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCJ/K0SfiDAYhHB9+KdzwABNDJsGaRi0tCKvAk4MrGPBv0WlXofttqS+sa3TSWz5hs3MEKEPwklKAf0G3lfFINaqPidrMXXN40DfFFXTL77ktvnkBK8L7lpdaucB64edrtlpJBEbXj/1pjMIWdgYM39nQ0ntZs+grzhnD7ZUR4+Sr2l+9kxKIgANbijvn3U0yF6REpEU+LDpWHwY/0W/TRgRA6XYRTdaTos+a6ChWvk2GOep7WHxyNA1G4Kh0zjsOcquFNrZYYyUPcq6EcCxNF+pXOG5sx4qAr8wkOa/rRWMv9CB4JubUd7QyxqoWRvE/eh/Ibx0+X82BIy4jctj/PjAgMBAAECggEAH5ldiP6ZQXUz29eIbdsZhS0O3vKAUqE0YJDvC4+QWdCf5KwWa2zUYRRMrW1dVPU9BkZm9SH6Vif0qlIgWUW5TumjBXirVb3C7Slq9zL8CWB4O28i2zWSoDPyOKkJlrBZ+czVvNXU/8GE/DoqEkgY5BszCATuqEIIPyIowltxMZJVtsoUiWoZMYPaEyvPnPCZRY9TZrrQYMAo6nz6VHoZ6oQwUUn/gFN2nztQnL+ivQa5fBgogOKtgC8P9HPKBzDb07YiO8QfC5kfc/DR8DWQm0T6466tKsvJ8KKaN8oE6dACMDKu5ZEwv54T9TOQiNZZdXDcdQTskWFYG+zJZg0VAQKBgQDMqhsGR3qerPAAMaX0vo5pL7Rc7Ypt+E2OxV/BjlpCRyX/SBbGku6NoutJiKsVF7tnfIqKkWRVClvefvDlhrorXhE8GQuTistgAreLoQ+ET8zoLGqJ1Ha0R6XEHsB6mktk3/F80jB/TknLQsECeZpeiwa70jSuSC5729B/ajIlHwKBgQCsmRWTzvTTkQC/Jh+VffmPhV3UZl+26rhGKLuNqH4g9jdBtOnzgUkoIGcYsYZJwgjzWgjkqt53YAN5ptDVtXo65xe0w8c0buNJbLBb13ttpXik3DuQQyco6u+NxdwHuV1M3rpV+zZloytDWfc1zppPMyr7Dq1h5qSDSD96Trf0vQKBgHwM4uJ0css4kSPc8/UbhyAUbSMiS5SKOyA9W+Pqz6R6kXphDIUBzBwHUsu4TdI0F0ALicGhjInLBJIgqQzGE5NzqcieeZjNOKYSH4Q/dDNDVzSmr1CpeeWdw8jSxuHJIcebwThiAkk3f5z28hv47us9IzC2oX29fIdQXdjM2mYLAoGBAJd8BKQmEFtf+PfLzgy29EIzG5dAAuHYQMPTHEnIiGsFrpnM30kJP9woFFRn+yThWFm9u/B2x6qeam5rKv9muFDaYsQU0NIEQRhAfMke5wXlJW4MnXqQdd5D1Nzpu6RL3MeK9KBiS+wIuf/zBjYDjwvULPUMfMBgXiObVZtTW8FBAoGAe8Wjh0nqjgnkddIiQx4Lh63W8dmm/bU7h8ZDnbHcEjuANZJks1qgJMU1Xj7YG23uGGd+k54iZQJODBB/hyd3M1XLHSO86N60REFbOTB16I2Kr6y0+qKVJei3BuAdG9bipg+MOEETBw8oMCOfKCmTjck/sXFL0rZImM7LbbXzfQQ=";

    /**
     * 支付宝公钥,
     */
    private String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhHZDaRNWBwdqeZDhc5GsRrh5I9fnNz6QPgfllqnoNwiCJin64T/2WwEwEE7H0hJiq2txdUgT6GLaTweHr0QhOw6nGIHBvxei1IbVDp5Ke1PKgZdGOz1sj1/hrUpyKpl4zAkGu2sZGHfxFQXvRui9yduLx0IIPC7rFHRd/qt8bACroNXwdp6OcPAljlatEdZm5W1vWqcO8v2NNq+UylVBPzYDPHUmsXUYYr4gif3YYqgUzWibNqNxOl2FmKaB9nuTNb/Tnm0tykTMFKDS+YiWXjgB52JP3/BadPguOZ7PMHa9OEeKWiTHJqecM3+kRXFw06+ltCDS6/DmgrkTPEEhXwIDAQAB";

    /**
     * 服务器异步通知页面路径需http://格式的完整路径，不能加?id=123这类自定义参数
     */
    private String notifyUrl = " http://8jpba2.natappfree.cc/pay/success";

    /**
     * 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数
     */
    private String returnUrl = "http://localhost:9528/#/order/index";

    /**
     * 签名方式
     */
    private String signType = "RSA2";

    /**
     * 字符编码格式
     */
    private String charset = "utf-8";

    /**
     * 支付宝网关
     */
    private String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    /**
     * 支付宝网关
     */
    private String logPath = "C:\\";


    @Bean
    public AlipayClient getAlipayClient(){
        // 支付宝网关
        String serverUrl = this.gatewayUrl;
        // APPID
        String appId = this.appId;
        // 私钥
        String privateKey = this.privateKey;
        // 格式化为 json 格式
        String format = "json";
        // 字符编码格式
        String charset = this.charset;
        // 公钥，对应APPID的那个
        String alipayPublicKey = this.getPublicKey();
        // 签名方式
        String signType = this.signType;

        return new DefaultAlipayClient(serverUrl, appId, privateKey, format, charset, alipayPublicKey, signType);
    }
}
