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
public class Demo02 {

    /**
     * RSA算法私钥
     */
    @Value("${crypto.RSA_PRIVATE_KEY}")
    private String RSA_PRIVATE_KEY;

    /**
     * RSA算法公钥
     */
    @Value("${crypto.RSA_PUBLIC_KEY}")
    private String RSA_PUBLIC_KEY;

    /**
     * 采用RSA算法生成签名、效验签名
     */
    @Test
    @SneakyThrows
    public void demo02(){
        //---------------------------------生成签名
        //要签名的内容
        String conent="蒋明辉是个大帅逼！我好爱。";
        String signature = DigitalSignatureUtil.generationSignature(conent, Base64.decodeBase64(RSA_PRIVATE_KEY));

        //---------------------------------效验签名
        boolean verifySignature = DigitalSignatureUtil.verifySignature(conent, signature, Base64.decodeBase64(RSA_PUBLIC_KEY));

        System.out.println("--------------------------生成的签名-----------------");
        System.out.println(signature);
        System.out.println("--------------------------效验签名、正确true、错误false-----------------");
        System.out.println(verifySignature);


    }
}
