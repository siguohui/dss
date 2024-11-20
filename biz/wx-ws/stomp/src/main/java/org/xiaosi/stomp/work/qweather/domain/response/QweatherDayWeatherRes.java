package org.xiaosi.stomp.work.qweather.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Description: 和风天气——逐天天气预报响应值
 *
 * @author kcaco
 * @since 2022-08-19 00:42
 */
@Data
public class QweatherDayWeatherRes {

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
     * 数据
     */
    private List<Daily> daily;

    @Data
    public static class Daily {
        /**
         * 预备时间
         */
        private String fxDate;

        /**
         * 当天最高气温
         */
        private String tempMax;

        /**
         * 当天最低气温
         */
        @JsonProperty("tempMin")
        private String tempMin;

        /**
         * 天气状态白天文字描述
         */
        private String textDay;

        /**
         * 天气状态晚间文字描述
         */
        private String textNight;

        /**
         * 白天风力等级
         */
        private String windScaleDay;

        /**
         * 白天风速，公里/小时
         */
        private String windSpeedDay;

        /**
         * 夜间风力等级
         */
        private String windScaleNight;

        /**
         * 夜间风速，公里/小时
         */
        private String windSpeedNight;

        /**
         * 紫外线强度指数
         */
        private String uvIndex;

        /**
         * 能见度，默认单位：公里
         */
        private String vis;
    }


}
