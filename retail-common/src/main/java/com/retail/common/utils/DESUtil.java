package com.retail.common.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
//DES 加密解密工具类
public class DESUtil {

    public static String aesDecryptForFront(String encryptStr,String decryptKey) {
        if (StringUtils.isEmpty(encryptStr) || StringUtils.isEmpty(decryptKey)) {
            return null;
        }
        try {
            byte[] encryptByte = Base64.getDecoder().decode(encryptStr);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
            byte[] decryptBytes = cipher.doFinal(encryptByte);
            return new String(decryptBytes);

        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }


    }

//    /**
//     * AES加密
//     * @param //content 明文
//     * @param //encryptKey 秘钥，必须为16个字符组成
//     * @return 密文
//     * @throws Exception
//     */
//    public static  String aesEncryptForFront() {
//        String content; String encryptKey;
//        content="1";
//        encryptKey="qqpqwertypuiopas";
//        if (StringUtils.isEmpty(content) || StringUtils.isEmpty(encryptKey)) {
//            return null;
//        }
//        try {
//            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
//
//            byte[] encryptStr = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
//            System.out.println(Base64.getEncoder().encodeToString(encryptStr));
//            return Base64.getEncoder().encodeToString(encryptStr);
//
//        } catch (Exception var3) {
//            var3.printStackTrace();
//            return null;
//        }
//    }
}
