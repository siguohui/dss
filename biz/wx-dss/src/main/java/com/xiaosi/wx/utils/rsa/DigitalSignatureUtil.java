package com.xiaosi.wx.utils.rsa;

import org.apache.commons.codec.binary.Base64;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 签名工具类
 */
public class DigitalSignatureUtil {

    /**
     * 参数分别代表
     */
    private static final String ALGORITHMS = "RSA";

    private static final String ALGORITHM = "SHA256withRSA";

    /**
     *
     * @param signatureContent 签名内容
     * @param privateKeyEncoded 私钥
     * @return
     * @throws Exception
     */
    public static String generationSignature(String signatureContent, byte[] privateKeyEncoded) throws Exception {
        // 创建key的工厂
        KeyFactory keyFactory=KeyFactory.getInstance(ALGORITHMS);
        // 创建 已编码的私钥规格
        PKCS8EncodedKeySpec encPriKeySpec = new PKCS8EncodedKeySpec(privateKeyEncoded);
        // 获取指定算法的密钥工厂, 根据 已编码的私钥规格, 生成私钥对象
        PrivateKey privateKey = keyFactory.generatePrivate(encPriKeySpec);
        Signature signature = Signature.getInstance(ALGORITHM);
        signature.initSign(privateKey);
        signature.update(signatureContent.getBytes());
        byte[] sign = signature.sign();
        // 采用base64算法进行转码,避免出现中文乱码
        return  Base64.encodeBase64String(sign);
    }

    /**
     *  校验签名
     * @param signatureContent 签名内容
     * @param signature 签名
     * @param publicKeyEncoded 公钥
     * @return
     * @throws Exception
     */
    public static boolean verifySignature(String signatureContent,String signature, byte[] publicKeyEncoded) throws Exception {
        // 创建key的工厂
        KeyFactory keyFactory=KeyFactory.getInstance(ALGORITHMS);
        // 创建 已编码的公钥规格
        X509EncodedKeySpec encPubKeySpec = new X509EncodedKeySpec(publicKeyEncoded);
        // 获取指定算法的密钥工厂, 根据 已编码的公钥规格, 生成公钥对象
        PublicKey publicKey = keyFactory.generatePublic(encPubKeySpec);
        Signature verifySignature = Signature.getInstance(ALGORITHM);
        verifySignature.initVerify(publicKey);
        verifySignature.update(signatureContent.getBytes());
        boolean verify = verifySignature.verify(Base64.decodeBase64(signature));
        return verify;
    }
}
