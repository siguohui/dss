package com.xiaosi.wx.config;

import com.xiaosi.wx.utils.RsaUtils;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.security.PrivateKey;
import java.security.PublicKey;

@Data
@Configuration
@ConfigurationProperties("rsa.key")
public class RsaKeyProperties {

    private Resource publicKey;
    private Resource privateKey;
    private PublicKey publicRsaKey;
    private PrivateKey privateRsaKey;

    @PostConstruct
    public void createKey() throws Exception {
        this.publicRsaKey = RsaUtils.getPublicKey(publicKey.getInputStream().readAllBytes());
        this.privateRsaKey = RsaUtils.getPrivateKey(privateKey.getInputStream().readAllBytes());
    }
}
