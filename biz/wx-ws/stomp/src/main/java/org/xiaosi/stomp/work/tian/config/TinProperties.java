package org.xiaosi.stomp.work.tian.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author kcaco
 * @since 2022-08-29 02:50
 */
@Data
@Component
@ConfigurationProperties(prefix = "api.tian")
public class TinProperties {

    /**
     * 密钥
     */
    private String key;
}
