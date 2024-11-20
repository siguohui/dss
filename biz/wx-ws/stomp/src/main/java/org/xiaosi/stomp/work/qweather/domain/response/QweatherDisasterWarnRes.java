package org.xiaosi.stomp.work.qweather.domain.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Description: 和风天气——天气灾害预警响应体
 *
 * @author kcaco
 * @since 2022-08-19 01:42
 */
@Data
@NoArgsConstructor
public class QweatherDisasterWarnRes {


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
     * 预警数据
     */
    private List<Warning> warning;

    @Data
    @NoArgsConstructor
    public static class Warning {
        /**
         * 本条预警的唯一标识，可判断本条预警是否已经存在
         */
        private String id;

        /**
         * 预警发布时间
         */
        private String pubTime;

        /**
         * 预警信息标题
         */
        private String title;

        /**
         * 预警开始时间，可能为空
         */
        private String startTime;

        /**
         * 预警结束时间，可能为空
         */
        private String endTime;

        /**
         * 预警信息的发布状态
         */
        private String status;

        /**
         * 预警严重等级
         */
        private String severity;

        /**
         * 预警类型名称
         */
        private String typeName;

        /**
         * 预警详细文字描述
         */
        private String text;

    }
}
