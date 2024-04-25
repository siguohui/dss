package com.xiaosi.wx.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor(staticName = "create")
public class JsonResult<T> implements Serializable {
    private Boolean success = Boolean.TRUE;
    private int code = 200;
    private String msg = "操作成功";
    private T data;

    public static <T> JsonResult<T> success() {
        return create(true,ResultEnum.RESULT_SUCCESS.getCode(),ResultEnum.RESULT_SUCCESS.getMsg(),null);
    }

    public static <T> JsonResult<T> success(String msg, T data) {
        return create(true,ResultEnum.RESULT_SUCCESS.getCode(),msg,data);
    }

    public static <T> JsonResult<T> success(T data) {
        return create(true,ResultEnum.RESULT_SUCCESS.getCode(),ResultEnum.RESULT_SUCCESS.getMsg(),data);
    }

    public static <T> JsonResult<T> success(String msg) {
        return create(true,ResultEnum.RESULT_SUCCESS.getCode(),msg,null);
    }

    public static <T> JsonResult<T> fail(String msg) {
        return create(false,ResultEnum.RESULT_FAIL.getCode(), msg,null);
    }

    public static <T> JsonResult<T> fail(Integer code, String msg) {
        return create(false,code, msg,null);
    }

    public static <T> JsonResult<T> fail(ResultEnum resultEnum) {
        return create(false,resultEnum.getCode(), resultEnum.getMsg(),null);
    }

    public static <T> JsonResult<T> fail(ResultEnum resultEnum, T data) {
        return create(false,resultEnum.getCode(), resultEnum.getMsg(),data);
    }

    //链式调用
    public JsonResult<T> builder(){
        return this;
    }
    public JsonResult<T> msg(String msg){
        this.setMsg(msg);
        return this;
    }
    public JsonResult<T> code(Integer code){
        this.setCode(code);
        return this;
    }
    public JsonResult<T> data(T data){
        this.setData(data);
        return this;
    }
}
