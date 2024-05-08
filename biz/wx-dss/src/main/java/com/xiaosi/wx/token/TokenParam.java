package com.xiaosi.wx.token;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "jjwt.token")
@Configuration(proxyBeanMethods = false)
public class TokenParam {
    private String jwtTokenSecret;
    private long tokenExpiredMs;
    private String tokenPrefix;
    private String tokenHeader;
}
