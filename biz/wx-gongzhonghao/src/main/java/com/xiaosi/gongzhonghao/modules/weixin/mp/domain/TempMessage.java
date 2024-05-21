package com.xiaosi.gongzhonghao.modules.weixin.mp.domain;

import lombok.Data;

/**
 * Description:
 *
 * @author kcaco
 * @since 2022-08-18 23:37
 */
@Data
public class TempMessage {

    /**
     * 用户id
     */
    private String userOpenid;

    /**
     * 模板消息id
     */
    private String messageTemplateId;
}
