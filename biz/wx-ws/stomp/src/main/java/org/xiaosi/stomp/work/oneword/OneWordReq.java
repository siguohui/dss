package org.xiaosi.stomp.work.oneword;

import lombok.Data;

/**
 * Description: 一言请求体
 *
 * @author kcaco
 * @since 2022-08-19 12:41
 */
@Data
public class OneWordReq {

    private String c = "j";
    private String encode = "text";
    private String charset;
    private String callback;
    private String select;
    private String min_length;
    private String max_length;

}
