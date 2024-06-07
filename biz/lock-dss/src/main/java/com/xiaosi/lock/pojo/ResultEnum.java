package com.xiaosi.lock.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
@AllArgsConstructor
public enum ResultEnum implements IResult, Serializable {

    RESULT_SUCCESS(200,"成功"),
    RESULT_FAIL(600,"失败"),
    BUSY(601,"系统繁忙"),
    MISSING_PARAMETER(601,"请求参数缺失"),
    METHOD_NOT_SUPPORT(602,"方法不支持"),
    NO_HANDLER_FOUND(603,"接口不存在"),
    PARAMETER_NOT_VALID(604,"参数格式错误"),
    PARAMETER_TOKEN_VALID(605,"TOKEN不正确"),
    PARAMETER_REPEART(606,"重复提交"),
    SYSTEM_ERROR(700, "系统错误"),
    SERIAL_ERROR(701, "序列化失败"),
    BIZ_CHECK_FAIL(702, "您的操作太快了,请稍后重试");

    private Integer code;
    private String msg;

    public static ResultEnum getValue(String code){
        for (ResultEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return SYSTEM_ERROR; //默认系统执行错误
    }
}
