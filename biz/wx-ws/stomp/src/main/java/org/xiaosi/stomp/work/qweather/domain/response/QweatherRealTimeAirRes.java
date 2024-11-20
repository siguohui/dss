package org.xiaosi.stomp.work.qweather.domain.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 和风天气——实时空气质量响应体
 *
 * @author kcaco
 * @since 2022-08-19 01:20
 */
@Data
@NoArgsConstructor
public class QweatherRealTimeAirRes {

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
     * 当前数据
     */
    private Now now;

    @Data
    @NoArgsConstructor
    public static class Now {
        /**
         * 空气质量数据发布时间
         */
        private String pubTime;

        /**
         * 空气质量指数
         */
        private String aqi;

        /**
         * 空气质量指数等级
         */
        private String level;

        /**
         * 空气质量指数级别
         */
        private String category;
    }
}
