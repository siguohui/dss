package com.xiaosi.gongzhonghao.config.api;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author kcaco
 * @since 2022-08-29 12:14
 */
@Data
@Component
@ConfigurationProperties(prefix = "api.work-wx")
public class WorkWeiXinProperties {

    /**
     * 微信企业号的appid
     */
    private String corpId;

    /**
     * 微信企业号的app corpSecret
     */
    private String corpSecret;

    /**
     * 应用id
     */
    private Integer agentId;

}
