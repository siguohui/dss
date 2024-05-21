package com.xiaosi.gongzhonghao.baseservice.qweather.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:和风天气——城市信息查询响应体
 *
 * @author kcaco
 * @since 2022-08-19 01:54
 */
@Data
@NoArgsConstructor
public class QweatherCityInfoRes {


    /**
     * 响应码
     */
    private String code;

    /**
     * 城市信息
     */
    private List<Location> location;


    @Data
    @NoArgsConstructor
    public static class Location {

        /**
         * 地区/城市名称
         */
        private String name;

        /**
         * 地区/城市ID
         */
        private String id;

        @JsonProperty("lat")
        private String lat;
        @JsonProperty("lon")
        private String lon;
        @JsonProperty("adm2")
        private String adm2;
        @JsonProperty("adm1")
        private String adm1;
        @JsonProperty("country")
        private String country;
        @JsonProperty("tz")
        private String tz;
        @JsonProperty("utcOffset")
        private String utcOffset;
        @JsonProperty("isDst")
        private String isDst;
        @JsonProperty("type")
        private String type;
        @JsonProperty("rank")
        private String rank;
        @JsonProperty("fxLink")
        private String fxLink;
    }


}
