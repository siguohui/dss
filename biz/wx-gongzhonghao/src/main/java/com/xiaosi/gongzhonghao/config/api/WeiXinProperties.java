package com.xiaosi.gongzhonghao.config.api;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author kcaco
 * @since 2022-08-29 02:46
 */
@Data
@Component
@ConfigurationProperties(prefix = "api.wx")
public class WeiXinProperties {

    /**
     * 应用id
     */
    private String appId;

    /**
     * 应用密钥
     */
    private String appSecret;

    /**
     * 用户id
     */
    private String userOpenid;

    /**
     * 消息模板id
     */
    private String msgTempId;
}
