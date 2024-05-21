package com.xiaosi.gongzhonghao.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author kcaco
 * @since 2022-08-29 02:26
 */
@Data
@Component
@ConfigurationProperties(prefix = "config.auth")
public class AuthConfig {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;


}
