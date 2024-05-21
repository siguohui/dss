package com.xiaosi.gongzhonghao.baseservice.qweather.domain.request;

import lombok.Data;

/**
 * Description: 和风天气通用请求体
 *
 * @author kcaco
 * @since 2022-08-19 00:32
 */
@Data
public class QweatherRequest {

    /**
     * 需要查询地区的LocationID或以英文逗号分隔的经度,纬度坐标（十进制，最多支持小数点后两位），LocationID可通过城市搜索服务获取。
     * <p>
     * 例如 location=101010100 或 location=116.41,39.92
     */
    private String location;

    /**
     * 用户认证key
     */
    private String key;

    /**
     * 生活指数的类型ID,非必填
     */
    private String type;

}
