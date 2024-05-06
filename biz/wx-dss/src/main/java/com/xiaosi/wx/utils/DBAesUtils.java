package com.xiaosi.wx.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

@Slf4j
public class DBAesUtils {
    /** AES 加解密密钥，请勿擅自修改！！！ */
    public static final String key = "a8skdjf238494720";


    /**
     * AES 加密 使用AES-128-ECB加密模式
     * @param sSrc  需要加密的字段
     * @param sKey  16 位密钥
     * @return
     * @throws Exception
     */
    public static String Encrypt(String sSrc, String sKey) {
        try {
            if (sKey == null) {
                return null;
            }
            /** 判断Key是否为16位 */
            if (sKey.length() != 16) {
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            /** "算法/模式/补码方式" */
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
            /** 此处使用BASE64做转码功能，同时能起到2次加密的作用。 */
            return new Base64().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String Encrypt(String sSrc) {
        return Encrypt(sSrc, key);
    }

    /**
     * AES 解密 使用AES-128-ECB加密模式
     * @param sSrc  需要解密的字段
     * @param sKey  16 位密钥
     * @return
     * @throws Exception
     */
    public static String Decrypt(String sSrc, String sKey) {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            /** 先用base64解密 */
            byte[] encrypted1 = new Base64().decode(sSrc);
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String Decrypt(String sSrc) {
        return Decrypt(sSrc, key);
    }
}
