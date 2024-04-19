package com.xiaosi.wx;

import com.alibaba.druid.filter.config.ConfigTools;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class EncryptedTest {

    public static final String PASSWORD;

    static {
        // 这里修改成加密前的原始密码
        PASSWORD = "root";
    }

    /**
     * 密码加密
     */
    @Test
    public void druidPasswordEncryptedTest() throws Exception {

        String[] encryptedPasswordAndPublicKey = ConfigTools.genKeyPair(512);
        String publicKey = encryptedPasswordAndPublicKey[0];
        String privateKey = encryptedPasswordAndPublicKey[1];


        // 使用公钥加密密码
        String encryptedPassword = ConfigTools.encrypt(publicKey, PASSWORD);

        // 原始密码
        System.out.println("rawPassword: " + PASSWORD);

        System.out.println("encryptedPassword: " + encryptedPassword);
        System.out.println("privateKey: " + privateKey);
        System.out.println("publicKey: " + publicKey);

    }
}