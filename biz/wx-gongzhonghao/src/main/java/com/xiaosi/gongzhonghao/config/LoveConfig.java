package com.xiaosi.gongzhonghao.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author kcaco
 * @since 2022-08-29 02:22
 */
@Data
@Component
@ConfigurationProperties(prefix = "config.love")
public class LoveConfig {

    /**
     * 女生生日
     */
    private String girlBirthday;

    /**
     * 男孩生日
     */
    private String boyBirthday;

    /**
     * 纪念日
     */
    private String day;


}
