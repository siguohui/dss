package org.xiaosi.stomp.work.qweather;

/**
 * Description: 和风天气Api请求常量
 *
 * @author kcaco
 * @since 2022-08-19 00:27
 */
public interface QweatherApiConstant {

    /**
     * 城市信息查询
     */
    String CITY_INFO_QUERY = "https://geoapi.qweather.com/v2/city/lookup?";

    /**
     * 实时天气
     */
    String REAL_TIME_WEATHER = "https://devapi.qweather.com/v7/weather/now?";

    /**
     * 逐小时预报（未来24小时）
     */
    String HOURLY_WEATHER_24 = "https://devapi.qweather.com/v7/weather/24h?";

    /**
     * 逐天天气预报（未来3天）
     */
    String DAY_WEATHER_3 = "https://devapi.qweather.com/v7/weather/3d?";

    /**
     * 天气生活指数（当天）
     */
    String WEATHER_LIFE_INDEX = "https://devapi.qweather.com/v7/indices/1d?";

    /**
     * 天气灾害预警
     */
    String WEATHER_DISASTER_WARNING = "https://devapi.qweather.com/v7/warning/now?";

    /**
     * 实时空气质量
     */
    String REAL_TIME_AIR_QUALITY = "https://devapi.qweather.com/v7/air/now?";


}
