package com.xiaosi.wx.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "create")
public class JsonResult<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T> JsonResult<T> success(String msg, T data) {
        return create(0,msg,data);
    }

    public static <T> JsonResult<T> success(String msg) {
        return create(0,msg,null);
    }

    public static <T> JsonResult<T> fail(String msg) {
        return create(1,msg,null);
    }

}
