package com.xiaosi.gongzhonghao.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Description: 默认配置
 *
 * @author kcaco
 * @since 2022-09-01 17:30
 */
@Data
@Component
@ConfigurationProperties(prefix = "config.default")
public class DefaultConfig {

    /**
     * 推送方式
     */
    private String push;

}
