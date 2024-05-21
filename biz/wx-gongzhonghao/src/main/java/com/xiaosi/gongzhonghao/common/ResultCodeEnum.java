package com.xiaosi.gongzhonghao.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCodeEnum {
    /**
     * 成功
     */
    SUCCESS(200, "成功"),

    /**
     * 失败
     */
    FAILED(500, "失败"),

    /**
     * 没有权限
     */
    NO_AUTH(999, "没有访问权限"),

    /**
     * 没有认证信息
     */
    NO_TOKEN(999, "没有认证信息"),

    /**
     * token失效
     */
    TOKEN_EXPIRE(401, "token已失效，请重新登录");

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 返回信息
     */
    private final String message;

}
