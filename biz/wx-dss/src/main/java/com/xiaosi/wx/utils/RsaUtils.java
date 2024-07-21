package com.xiaosi.wx.utils;


import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * AES 简介
 *
 * AES加密解密算法是一种可逆的对称加密算法，这类算法在加密和AES解密时使用相同的密钥，或是使用两个可以简单地相互推算的密钥，一般用于服务端对服务端之间对数据进行加密解密。它是一种为了替代原先DES、3DES而建立的高级加密标准（Advanced Encryption Standard）。
 *
 * 作为可逆且对称的块加密，AES加密算法的速度比公钥加密等加密算法快很多，在很多场合都需要AES对称加密，但是要求加密端和解密端双方都使用相同的密钥是AES算法的主要缺点之一。
 *
 * AES加密解密
 *
 * AES加密需要：明文 + 密钥+ 偏移量（IV）+密码模式(算法/模式/填充) AES解密需要：密文 + 密钥+ 偏移量（IV）+密码模式(算法/模式/填充)
 *
 * AES的算法模式一般为 AES/CBC/PKCS5Padding 或 AES/CBC/PKCS7Padding
 *
 * AES常见的工作模式：
 *
 * 电码本模式(ECB)
 * 密码分组链接模式(CBC)
 * 计算器模式(CTR)
 * 密码反馈模式(CFB)
 * 输出反馈模式(OFB)
 * 除了ECB无须设置初始化向量IV而不安全之外，其它AES工作模式都必须设置向量IV，其中GCM工作模式较为特殊。
 *
 * AES填充模式
 *
 * 块密码只能对确定长度的数据块进行处理，而消息的长度通常是可变的，因此需要选择填充模式。
 *
 * 填充区别：在ECB、CBC工作模式下最后一块要在加密前进行填充，其它不用选择填充模式；
 *
 * 填充模式：AES支持的填充模式为PKCS7和NONE不填充。其中PKCS7标准是主流加密算法都遵循的数据填充算法。AES标准规定的区块长度为固定值128Bit，对应的字节长度为16位，这明显和PKCS5标准规定使用的固定值8位不符，虽然有些框架特殊处理后可以通用PKCS5，但是从长远和兼容性考虑，推荐PKCS7。
 *
 * AES密钥KEY和初始化向量IV
 *
 * 初始化向量IV可以有效提升安全性，但是在实际的使用场景中，它不能像密钥KEY那样直接保存在配置文件或固定写死在代码中，一般正确的处理方式为：在加密端将IV设置为一个16位的随机值，然后和加密文本一起返给解密端即可。
 *
 * 密钥KEY：AES标准规定区块长度只有一个值，固定为128Bit，对应的字节为16位。AES算法规定密钥长度只有三个值，128Bit、192Bit、256Bit，对应的字节为16位、24位和32位，其中密钥KEY不能公开传输，用于加密解密数据；
 *
 * 初始化向量IV：该字段可以公开，用于将加密随机化。同样的明文被多次加密也会产生不同的密文，避免了较慢的重新产生密钥的过程，初始化向量与密钥相比有不同的安全性需求，因此IV通常无须保密。然而在大多数情况中，不应当在使用同一密钥的情况下两次使用同一个IV，一般推荐初始化向量IV为16位的随机值。
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 * RSA非对称加密工具类
 *
 * RSA加密解密算法支持三种填充模式，
 *
 * 分别是ENCRYPTION_OAEP、ENCRYPTION_PKCS1、ENCRYPTION_NONE，RSA填充是为了和公钥等长。
 *
 * ENCRYPTION_OAEP：最优非对称加密填充，是RSA加密和RSA解密最新最安全的推荐填充模式。
 *
 * ENCRYPTION_PKCS1：随机填充数据模式，每次加密的结果都不一样，是RSA加密和RSA解密使用最为广泛的填充模式。
 *
 * ENCRYPTION_NONE：不填充模式，是RSA加密和RSA解密使用较少的填充模式。
 *
 * RSA 常用的加密填充模式
 *
 * RSA/None/PKCS1Padding
 * RSA/ECB/PKCS1Padding
 * 知识点：
 *
 * Java 默认的 RSA 实现是 RSA/None/PKCS1Padding
 *
 * 在创建RSA秘钥对时，长度最好选择 2048的整数倍，长度为1024在已经不很安全了
 *
 * 一般由服务器创建秘钥对，私钥保存在服务器，公钥下发至客户端
 *
 * DER是RSA密钥的二进制格式，PEM是DER转码为Base64的字符格式，由于DER是二进制格式，不便于阅读和理解。一般而言，密钥都是通过PEM的格式进行存储的
 * @author 赖柄沣 bingfengdev@aliyun.com
 * @version 1.0
 * @date 2020/9/2 22:27
 */
public class RsaUtils {

    private static final int DEFAULT_KEY_SIZE = 2048;
    private static final String ALGORITHM_RSA = "RSA";
    private static final String SIGNATRUE_RSA = "SHA256withRSA";
    public static final String SIGN_ALGORITHMS = "MD5WithRSA";

    /**从文件中读取公钥
     * @author 赖柄沣 bingfengdev@aliyun.com
     * @date 2020-09-04 13:10:15
     * @param filename 公钥保存路径，相对于classpath
     * @return java.security.PublicKey 公钥对象
     * @throws Exception
     * @version 1.0
     */
    public static PublicKey getPublicKey(String filename) throws Exception {

        byte[] bytes = readFile(filename);
        return getPublicKey(bytes);
    }


    /**从文件中读取密钥
     * @author 赖柄沣 bingfengdev@aliyun.com
     * @date 2020-09-04 13:12:01
     * @param filename 私钥保存路径，相对于classpath
     * @return java.security.PrivateKey 私钥对象
     * @throws Exception
     * @version 1.0
     */
    public static PrivateKey getPrivateKey(String filename) throws Exception {
        byte[] bytes = readFile(filename);
        return getPrivateKey(bytes);

    }

    /**
     * @author 赖柄沣 bingfengdev@aliyun.com
     * @date 2020-09-04 13:12:59
     * @param bytes 公钥的字节形式
     * @return java.security.PublicKey 公钥对象
     * @throws Exception
     * @version 1.0
     */
    public static PublicKey getPublicKey(byte[] bytes) throws Exception {
        bytes = Base64.getDecoder().decode(bytes);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance(ALGORITHM_RSA);
        return factory.generatePublic(spec);

    }


    /**获取密钥
     * @author 赖柄沣 bingfengdev@aliyun.com
     * @date 2020-09-04 13:14:02
     * @param bytes 私钥的字节形式
     * @return java.security.PrivateKey
     * @throws Exception
     * @version 1.0
     */
    public static PrivateKey getPrivateKey(byte[] bytes) throws InvalidKeySpecException, NoSuchAlgorithmException {
        bytes = Base64.getDecoder().decode(bytes);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance(ALGORITHM_RSA);
        return factory.generatePrivate(spec);

    }

    public static KeyPair getKeyPair(String secret, int keySize) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM_RSA);
        SecureRandom secureRandom = new SecureRandom(secret.getBytes());
        keyPairGenerator.initialize(Math.max(keySize, DEFAULT_KEY_SIZE), secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        return keyPair;
    }

    /**
     * 根据密文，生存rsa公钥和私钥,并写入指定文件
     *@author 赖柄沣 bingfengdev@aliyun.com
     *@date 2020-09-04 13:14:02
     * @param publicKeyFilename  公钥文件路径
     * @param privateKeyFilename 私钥文件路径
     * @param secret             生成密钥的密文
     */
    public static void generateKey(String publicKeyFilename, String privateKeyFilename, String secret, int keySize) throws Exception {
        KeyPair keyPair = getKeyPair(secret,keySize);
        // 获取公钥并写出
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        publicKeyBytes = Base64.getEncoder().encode(publicKeyBytes);
        writeFile(publicKeyFilename, publicKeyBytes);
        // 获取私钥并写出
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        privateKeyBytes = Base64.getEncoder().encode(privateKeyBytes);
        writeFile(privateKeyFilename, privateKeyBytes);
    }

    /**读文件
     * @author 赖柄沣 bingfengdev@aliyun.com
     * @date 2020-09-04 13:15:37
     * @param fileName
     * @return byte[]
     * @throws
     * @version 1.0
     */
    private static byte[] readFile(String fileName) throws Exception {
        return Files.readAllBytes(new File(fileName).toPath());

    }

    /**写文件
     * @author 赖柄沣 bingfengdev@aliyun.com
     * @date 2020-09-04 13:16:01
     * @param destPath
     * @param bytes
     * @return void
     * @throws
     * @version 1.0
     */
    private static void writeFile(String destPath, byte[] bytes) throws IOException {
        File dest = new File(destPath);
        if (!dest.exists()) {
            dest.createNewFile();
        }
        Files.write(dest.toPath(), bytes);

    }

    //加签
    public static byte[] sign(String signatrue, PrivateKey privateKey, String msg) throws Exception {
        Signature signature = Signature.getInstance(signatrue);
        signature.initSign(privateKey);
        signature.update(msg.getBytes());
        return signature.sign();
    }

    //验签
    public static Boolean verify(String signatrue, PublicKey publicKey, String msg, byte[] msgBytes) throws Exception {
        Signature verification = Signature.getInstance(signatrue);
        verification.initVerify(publicKey);
        verification.update(msg.getBytes());
        return verification.verify(msgBytes);
    }

    /**构造器私有化
     * @author 赖柄沣 bingfengdev@aliyun.com
     * @date 2020-09-04 13:16:29
     * @param
     * @return
     * @throws
     * @version 1.0
     */
    private RsaUtils() {
    }

    private String publicFile = "D:\\keys\\rsa_key.pub";
    private String privateFile = "D:\\keys\\rsa_key";


    /**生成公钥和私钥
     * @author 赖柄沣 bingfengdev@aliyun.com
     * @date 2020-09-03 10:32:16
     * @throws Exception
     * @version 1.0
     */
    @Test
    public void generateKey() throws Exception{
        RsaUtils.generateKey(publicFile,privateFile,"test123",2048);
        KeyPair keyPair = getKeyPair("test123", 2048);

        // 待签名的字符串
        String message = "Hello, World!";
        byte[] sign = sign(SIGNATRUE_RSA, keyPair.getPrivate(), message);
        System.out.println(verify(SIGNATRUE_RSA, keyPair.getPublic(),message,sign));

        String test = sign("123456", keyPair.getPrivate().toString());
        boolean res = doCheck("123456", test, keyPair.getPublic().toString());
        System.out.println(res);
    }

    /**
     * @param content:签名的参数内容
     * @param privateKey：私钥
     * @return
     */
    public static String sign(String content, String privateKey) {
        String charset = "utf-8";
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(com.xiaosi.wx.utils.Base64.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update(content.getBytes(charset));

            byte[] signed = signature.sign();

            return com.xiaosi.wx.utils.Base64.encode(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param content：验证参数的内容
     * @param sign：签名
     * @param publicKey：公钥
     * @return
     */
    public static boolean doCheck(String content, String sign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = com.xiaosi.wx.utils.Base64.decode(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

            signature.initVerify(pubKey);
            signature.update(content.getBytes("utf-8"));

            boolean bverify = signature.verify(com.xiaosi.wx.utils.Base64.decode(sign));
            return bverify;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
