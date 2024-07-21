package com.xiaosi.wx.utils.RsaAes;

import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.ServiceException;

import java.security.interfaces.RSAPrivateKey;
import java.util.Objects;

/**
 * @module
 * @author: qingxu.liu
 * @date: 2023-02-09 17:43
 * @copyright
 **/
public class RequestDecryptionUtil {

    private final static String publicKey = "RSA生成的公钥";
    private final static String privateKey = "RSA生成的私钥";
    private final static Integer timeout = 60000;

    /**
     *
     * @param sym RSA 密文
     * @param asy AES 密文
     * @param clazz 接口入参类
     * @return Object
     */
    public static <T> Object getRequestDecryption(String sym, String asy, Class<T> clazz){
        //验证密钥
        try {
            //解密RSA
            RSAPrivateKey rsaPrivateKey = ActivityRSAUtil.getRSAPrivateKeyByString(privateKey);
            String RSAJson = ActivityRSAUtil.privateDecrypt(sym, rsaPrivateKey);
            RSADecodeData rsaDecodeData = JSONObject.parseObject(RSAJson, RSADecodeData.class);
            boolean isTimeout = Objects.nonNull(rsaDecodeData)  && Objects.nonNull(rsaDecodeData.getTime()) && System.currentTimeMillis() -  rsaDecodeData.getTime() < timeout;
            if (!isTimeout){
                throw new ServiceException("Request timed out, please try again."); //请求超时
            }
            //解密AES
            String AESJson = AES256Util.decode(rsaDecodeData.getKey(),asy,rsaDecodeData.getKeyVI());
            System.out.println("AESJson: "+AESJson);
            return JSONObject.parseObject(AESJson,clazz);
        } catch (Exception e) {
            throw new RuntimeException("RSA decryption Exception:  " +e.getMessage());
        }
    }

    public static JSONObject getRequestDecryption(String sym, String asy){
        //验证密钥
        try {
            //解密RSA
            RSAPrivateKey rsaPrivateKey = ActivityRSAUtil.getRSAPrivateKeyByString(privateKey);
            String RSAJson = ActivityRSAUtil.privateDecrypt(sym, rsaPrivateKey);
            RSADecodeData rsaDecodeData = JSONObject.parseObject(RSAJson, RSADecodeData.class);
            boolean isTimeout = Objects.nonNull(rsaDecodeData)  && Objects.nonNull(rsaDecodeData.getTime()) && System.currentTimeMillis() -  rsaDecodeData.getTime() < timeout;
            if (!isTimeout){
                throw new ServiceException("Request timed out, please try again."); //请求超时
            }
            //解密AES
            String AESJson = AES256Util.decode(rsaDecodeData.getKey(),asy,rsaDecodeData.getKeyVI());
            System.out.println("AESJson: "+AESJson);
            return JSONObject.parseObject(AESJson);
        } catch (Exception e) {
            throw new RuntimeException("RSA decryption Exception:  " +e.getMessage());
        }
    }
}
