package com.xiaosi.gongzhonghao.common;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回结果
 *
 * @author cacok
 */
@Data
@NoArgsConstructor
public class Result implements Serializable {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 数据
     */
    private Object data;

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    /**
     * 带消息成功返回
     */
    public static Result successWithMsg(String msg) {
        return new Result(ResultCodeEnum.SUCCESS.getCode(), msg, null);
    }

    /**
     * 带数据成功返回
     */
    public static Result success(Object data) {
        return new Result(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 不带数据成功返回
     */
    public static Result success() {
        return new Result(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), null);
    }

    /**
     * 错误返回，指定错误消息
     */
    public static Result failed(String msg) {
        return new Result(ResultCodeEnum.FAILED.getCode(), msg, null);
    }

    /**
     * 错误返回，指定错误消息
     */
    public static Result failed(Integer code, String msg) {
        return new Result(code, msg, null);
    }

    /**
     * 错误返回，默认错误消息
     */
    public static Result failed() {
        return failed(ResultCodeEnum.FAILED.getMessage());
    }


    public static Result failed(Integer code, String msg, Object data) {
        return new Result(code, msg, data);
    }

    /**
     * 没有权限
     */
    public static Result noAuth() {
        return new Result(ResultCodeEnum.NO_AUTH.getCode(), ResultCodeEnum.NO_AUTH.getMessage(), null);
    }

    /**
     * 没有权限
     */
    public static Result noAuth(String data) {
        return new Result(ResultCodeEnum.NO_AUTH.getCode(), ResultCodeEnum.NO_AUTH.getMessage(), data);
    }


    /**
     * token失效
     */
    public static Result tokenExpired() {
        return new Result(ResultCodeEnum.TOKEN_EXPIRE.getCode(), ResultCodeEnum.TOKEN_EXPIRE.getMessage(), null);
    }

    /**
     * token失效  自定义消息
     */
    public static Result tokenExpired(String msg) {
        return new Result(ResultCodeEnum.TOKEN_EXPIRE.getCode(), msg, null);
    }

}
