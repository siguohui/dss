package org.xiaosi.stomp.work.qweather.domain.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 *
 * @author kcaco
 * @since 2022-08-19 02:37
 */
@Data
@NoArgsConstructor
public class QweatherRealTimeWeatherRes {


    /**
     * 状态码
     */
    private String code;

    /**
     * 当前API的最近更新时间
     */
    private String updateTime;

    /**
     * 当前数据的响应式页面，便于嵌入网站或应用
     */
    private String fxLink;

    /**
     * 实时天气数据
     */
    private Now now;

    @Data
    @NoArgsConstructor
    public static class Now {
        /**
         * 数据观测时间
         */
        private String obsTime;

        /**
         * 温度，默认单位：摄氏度
         */
        private String temp;

        /**
         * 体感温度，默认单位：摄氏度
         */
        private String feelsLike;

        /**
         * 天气状况的文字描述，包括阴晴雨雪等天气状态的描述
         */
        private String text;

        /**
         * 风力等级
         */
        private String windScale;

        /**
         * 能见度，默认单位：公里
         */
        private String vis;

    }
}
