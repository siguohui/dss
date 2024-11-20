package org.xiaosi.stomp.work.qweather;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xiaosi.stomp.work.qweather.config.QweatherProperties;
import org.xiaosi.stomp.work.qweather.domain.request.QweatherRequest;
import org.xiaosi.stomp.work.qweather.domain.response.*;
import org.xiaosi.stomp.work.qweather.okhttp.GeneralOkhttpService;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 和风天气工具类
 *
 * @author kcaco
 * @since 2022-08-19 00:35
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QweatherService {

    private final QweatherProperties qweatherProperties;

    private final GeneralOkhttpService generalOkhttpService;


    /**
     * 通用获取数据方法
     *
     * @param qweatherRequest 请求体
     * @param reqUrl          请求地址
     * @param resClazz        响应体Class
     * @param <T>             泛型
     * @return T 响应体
     */
    public <T> T generalGetMethod(QweatherRequest qweatherRequest, String reqUrl, Class<T> resClazz, String errMsg) {
        // 如果没有传location，则使用默认配置的location
        String location = StrUtil.blankToDefault(qweatherRequest.getLocation(), qweatherProperties.getLocationId());
        if (StrUtil.isBlank(location)) {
            throw new RuntimeException("尚未配置location");
        }

        // 如果没有传key，则使用默认配置的key
        String token = StrUtil.blankToDefault(qweatherRequest.getKey(), qweatherProperties.getKey());
        if (StrUtil.isBlank(token)) {
            throw new RuntimeException("尚未配置key");
        }

        // 格式化请求地址
        Map<String, String> bodyParams = new HashMap<>();
        bodyParams.put("location", location);
        bodyParams.put("key", token);
        String suffix = "location={location}&key={key}";
        if (StrUtil.isNotBlank(qweatherRequest.getType())) {
            bodyParams.put("type", qweatherRequest.getType());
            suffix = suffix + "&type={type}";
        }
        String url = StrUtil.format(reqUrl + suffix, bodyParams);

        return generalOkhttpService.generalGetMethod(url, resClazz, errMsg);
    }

    /**
     * 城市信息
     */
    public QweatherCityInfoRes getCityInfo(QweatherRequest qweatherRequest) {
        return generalGetMethod(qweatherRequest,
                QweatherApiConstant.CITY_INFO_QUERY,
                QweatherCityInfoRes.class,
                "获取城市信息失败!");
    }

    /**
     * 实时天气
     */
    public QweatherRealTimeWeatherRes getRealTimeWeather(QweatherRequest qweatherRequest) {
        return generalGetMethod(qweatherRequest,
                QweatherApiConstant.REAL_TIME_WEATHER,
                QweatherRealTimeWeatherRes.class,
                "获取实时天气失败!");
    }

    /**
     * 逐天天气预报（为例3天）
     */
    public QweatherDayWeatherRes getDayWeather3(QweatherRequest qweatherRequest) {
        return generalGetMethod(qweatherRequest,
                QweatherApiConstant.DAY_WEATHER_3,
                QweatherDayWeatherRes.class,
                "获取逐小时预报（未来24小时）失败!");
    }

    /**
     * 天气灾害预警
     */
    public QweatherDisasterWarnRes getWeatherDisasterWarning(QweatherRequest qweatherRequest) {
        return generalGetMethod(qweatherRequest,
                QweatherApiConstant.WEATHER_DISASTER_WARNING,
                QweatherDisasterWarnRes.class,
                "获取天气灾害预警失败!");
    }

    /**
     * 实时空气质量
     */
    public QweatherRealTimeAirRes getRealTimeAirQuality(QweatherRequest qweatherRequest) {
        return generalGetMethod(qweatherRequest,
                QweatherApiConstant.REAL_TIME_AIR_QUALITY,
                QweatherRealTimeAirRes.class,
                "获取实时空气质量失败!");
    }

    /**
     * 生活指数
     */
    public QweatherLifeIndexRes getLifeIndex(QweatherRequest qweatherRequest) {
        qweatherRequest.setType("1,3,16");
        return generalGetMethod(qweatherRequest,
                QweatherApiConstant.WEATHER_LIFE_INDEX,
                QweatherLifeIndexRes.class,
                "获取实时空气质量失败!");
    }

}
