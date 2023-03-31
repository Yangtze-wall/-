package com.retail.order.utils;
/**
 * @ProjectName:    limeibo-yuekao-3.17
 * @Package:        com.bwie.config
 * @ClassName:      AliPayConfig
 * @Author:     2766395184
 * @Description:
 * @Date:    2023/3/28 21:20
 * @Version:    1.0
 */

public class AliPayConfig {
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号 按照我文章图上的信息填写
    public static String app_id ="2021000122673014";

    // 商户私钥，您的PKCS8格式RSA2私钥  刚刚生成的私钥直接复制填写
    public static String merchant_private_key="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCCcROJsz76gsdTjQJ4Hm1SnrbzW40yPiCQmfo1V35MFb2sZdFx4ceoECCOQAZm1Wv3FfNxDS9kAJASZaCnU1T6Fa77Y2uWp12XqJP6cRTsQSVmt+8pJvdip54ekFRgG4m+Gm6GnVMIh33+lFpAniL6ct66joCaofFWtCjyO6u/JDcw0EIJg/wGJkJDV5KVQS2nIVy+BVzADFBOwEx5bB4NGFuFfkoTslYigdqmGaQaHj5cKsQSJOr1wn24pH2ADXf65RvF+J22jH6j7vGPe1+rzsQ+0mASfENEfN+c+rhxKOUPCbU0S7wvbeSr2SMv/aJo+xiGHVGI4aasrUIgqSeZAgMBAAECggEAMFXwdH08YB3n+Njuyk4UGqJ/jswlFTIaZRr5sZx7S3xe7axJIVcV4fwf5DZraN27BTNYI1NMdVUUDg0kq/xgJ8+zKKpdqtTYBKPOOeZ/Z1B4qIiOEgTDdQZBvHelHHv33b7zp+RSaX2zvYuBqiIvOfko+Z5mn2ZVBNj7t09PRCOCaOeZ9uJS+4SR3k2yOhWwI7Y0rzTw9Mc8fuqWW9oNNDkCj6zX/eJV03aBQfgs8aqJJSX8lDJ7VTDf96vaczuCUQGTgF8ucjKuaTUsvjqKVzcWqxsmgWeNkQqnqtDujWYlTbn9YVt5bHtlvimyI8z+CyP6cC2omoP3w4j6cJNLAQKBgQDH+bKFwe79cxIETDTdtxoDNRRe4CKt5Yr/t1628w/ADaDAaSoIuDSnLXScDd48R5PTyzGLakKdziO4hxAvWIXJ1Wsghr1oPyfBx2artgsU//8PbX2eAvu9r8GJr5lNngrOxN6sFFS5wQJLooSjXxX0w9AiXHHvNfQbQPIh3EgreQKBgQCm/GZjhpXe64xGHYLcQMpjR+8SHNJ1PJoogB2BFz/MlfSJC747fRgA2HsW+Ykz4CpwIrKYolP28y9FdOuTtMwBH6CJbl2Y26msMlAQ7/kta8yGJgNEEspVZm5le0XuhsowxXKceFii8m7ny+km8jGQsmWr6ty92pSM6ZP0Qte1IQKBgQCsN6Y++x1OuGKzwM1mg+GrEXNRocU77YsfXlP7SqgtxY7DJc5FZB40OzlJi8fwMdRleYBZiFGGhZjNucN+gsX1ZA62HFPSC1G52HfbOROiKjUGnEyqX/ddDJeoc8maxpiYKYN6vqeYsVfAu11vNNw7xsbgUKzuTZDER9I+xu6AQQKBgE9OLf50i7cMXVlraJvcNYKDRlpuz9EYEArOFbUaapVqXnG73wP3vC+ioIrfxjaZ7u3EvNdauM/e3QN6rOtAgWbDM4YxoUgYgvOiDX6NyeS1frPzV7e7yNZYSHPv4N2QrHQPQghn64iv5NY3dKqnebzDzR+h8mZEaiqmQDtQh4uhAoGBALoPV4EBsOUjuPJ2a+won9+AiuXEGNSCLFGETLVM8Ys/FHtm38EBURjIgPcKd0kpybxjddaAHyq3c9bIWUblFDV9rLFS6I6K0y9WU8odU7LIDIXB0TlZip6Xvn369ivMZ1GrjAg1G6chF3a1T9NawUr/Tsy/maFBHNORLdrFnFzN";

    // 支付宝公钥,对应APPID下的支付宝公钥。 按照我文章图上的信息填写支付宝公钥，别填成商户公钥
    public static String alipay_public_key ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAg4byMagduOO4usBeWd0PxCdQ0eqgybcFuMEntuUDAgK3Xqq88WAwZxdQaBdG6mJSLZyaLgR0AzCk8MgoOeap56MEBCZuU/2EcGELS0UJTZWyzQFw8Wx34cMu9KJw4dA1EXVmwJOkgg2y9OT6ST6fNCk0gVmlYHpBVINH2pXcc0HLSZRfvXMiyno+vG2su/Ki20dda+Cb4Ncp+YHIlXcjhl7NFntJ6tOJoJcZRxxzPn5qn+OjbARLl08t4Z4bQHoNVM9ooxHsVvfTdUBTmYKNOm0pTM7RAJhkSdx9Ug+wmwGJnjI//WcxUMiloL/KRuegf6P2JEH0nNLJ0XAJXEexawIDAQAB";
    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，其实就是你的一个支付完成后返回的页面URL
//    public static String notify_url = neturl+"/alipay/notify_url";
    public static String notify_url ="http://imtr6m.natappfree.cc/payment/payed/notify";
    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，其实就是你的一个支付完成后返回的页面URL
//    public static String return_url = neturl+"/alipay/return_url";
    public static String return_url ="http://www.baidu.com";
    // 签名方式z
    public static String sign_type ="RSA2";

    // 字符编码格式
    public static String charset ="utf-8";

    // 支付宝网关
    public static String gatewayUrl ="https://openapi.alipaydev.com/gateway.do";

}
