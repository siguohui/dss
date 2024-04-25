package com.xiaosi.wx.exception;

import com.xiaosi.wx.pojo.ResultEnum;
import lombok.Getter;

import java.util.Optional;

@Getter
public abstract  class AbstractException  extends RuntimeException{
    private final Integer code;
    private final String msg;

    public AbstractException(ResultEnum resultEnum, String msg,
                             Throwable throwable){
        super(msg,throwable);
        this.code = resultEnum.getCode();
        this.msg = Optional.ofNullable(msg).orElse(resultEnum.getMsg());
    }
}
