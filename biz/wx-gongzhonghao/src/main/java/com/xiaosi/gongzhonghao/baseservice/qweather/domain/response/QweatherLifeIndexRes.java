package com.xiaosi.gongzhonghao.baseservice.qweather.domain.response;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 *
 * @author kcaco
 * @since 2022-08-19 10:59
 */
@Data
@NoArgsConstructor
public class QweatherLifeIndexRes {

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
    @NoArgsConstructor
    public static class Daily {
        /**
         * 预报日期
         */
        private String date;

        /**
         * 生活指数类型ID
         */
        private String type;

        /**
         * 生活指数类型的名称
         */
        private String name;

        /**
         * 生活指数预报等级
         */
        private String level;

        /**
         * 生活指数预报级别名称
         */
        private String category;

        /**
         * 生活指数预报的详细描述，可能为空
         */
        private String text;
    }
}
