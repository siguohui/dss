package com.xiaosi.back.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "build")
public class ApiResult<T> {

    private Integer code;
    private String msg;
    private T t;

    public static<T> ApiResult<T> ok(T t){
        return ApiResult.build(200,"成功", t);
    }
}
