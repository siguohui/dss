package org.xiaosi.stomp.work.qweather.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Description: 和风天气配置
 *
 * @author kcaco
 * @since 2022-08-29 02:29
 */
@Data
@Component
@ConfigurationProperties(prefix = "api.qweather")
public class QweatherProperties {

    /**
     * 用户认证key
     */
    private String key;

    /**
     * 位置id
     */
    private String locationId;

}
