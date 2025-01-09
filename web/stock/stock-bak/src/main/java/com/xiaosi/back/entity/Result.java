package com.xiaosi.back.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor(staticName = "build")
public class Result<T> {

    private Boolean success;
    private Integer code;
    private String msg;
    private T data;

    public static <T> Result<T> success(T data){
        return Result.build(true, 200, null, data);
    }

    public static <T> Result<T> fail(String msg){
        return Result.build(false, 303,  msg,null);
    }

}
