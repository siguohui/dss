package com.xiaosi.wx.utils.rsa;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author 蒋明辉
 * @data 2023/6/18 23:25
 */
@Slf4j
public class Demo03 {

    /**
     * RSA签名私钥
     */
    @Value("${crypto.SIGNATURE_PRIVATE_KEY}")
    private String SIGNATURE_PRIVATE_KEY;

    /**
     * RSA签名公钥
     */
    @Value("${crypto.SIGNATURE_PUBLIC_KEY}")
    private String SIGNATURE_PUBLIC_KEY;


    /**
     * 采用RSA算法加解密
     */
    @Test
    @SneakyThrows
    public void demo03(){
        //------------------------RSA算法加密
        //要加密的内容
        String conent="蒋明辉好帅！我好爱。";
        String encrypt = RSAEncryptUtil.encrypt(conent, Base64.decodeBase64(SIGNATURE_PUBLIC_KEY));

        //------------------------RSA算法解密
        String decrypt = RSAEncryptUtil.decrypt(encrypt, Base64.decodeBase64(SIGNATURE_PRIVATE_KEY));

        System.out.println("-----------------加密------------------");
        System.out.println(encrypt);
        System.out.println("-----------------解密------------------");
        System.out.println(decrypt);
    }

}
