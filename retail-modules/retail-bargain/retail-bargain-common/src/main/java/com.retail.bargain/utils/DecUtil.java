package com.retail.bargain.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author DecUtil
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.bargain.utils
 * @date: 2023-03-26  21:54
 * @Created by:  12871
 * @Description:
 * @Version:
 */
public class DecUtil {
//        public static void main(String[] args) throws Exception {
//            String key = "abcdefgh"; // 密钥，必须是8个字符
//            String originalString = "1";
//
//            // 加密操作
//            byte[] encodedBytes = encrypt(originalString.getBytes(StandardCharsets.UTF_8), key);
//            String encodedString = Base64.getEncoder().encodeToString(encodedBytes);
//            System.out.println("Encoded string: " + encodedString);
//
//            // 解密操作
//            byte[] decodedBytes = decode(Base64.getDecoder().decode(encodedString), key);
//            String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
//            System.out.println("Decoded string: " + decodedString);
//        }

        public static byte[] encrypt(byte[] originalBytes, String key) throws Exception {
            Cipher cipher = Cipher.getInstance("DES");
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(originalBytes);
        }

        public static byte[] decode(byte[] encryptedBytes, String key) throws Exception {
            Cipher cipher = Cipher.getInstance("DES");
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(encryptedBytes);
        }


}
