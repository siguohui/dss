package org.xiaosi.stomp.work.tian;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Description:
 *
 * @author kcaco
 * @since 2022-08-19 13:35
 */
@Data
@NoArgsConstructor
public class TianRes {
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 消息
     */
    private String msg;

    /**
     * 数据
     */
    private List<Newslist> newslist;

    @Data
    @NoArgsConstructor
    public static class Newslist {

        /**
         * 内容
         */
        private String content;
    }


}
