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

/**RSA非对称加密工具类
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
