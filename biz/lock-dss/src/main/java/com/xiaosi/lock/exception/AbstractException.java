package com.xiaosi.lock.exception;

import com.xiaosi.lock.pojo.ResultEnum;
import lombok.Getter;

import java.util.Optional;

@Getter
public abstract  class AbstractException  extends RuntimeException{
    private final Integer code;
    private final String msg;

    public AbstractException(ResultEnum resultEnum, String msg,
                             Throwable throwable){
        super(msg,throwable);
        resultEnum = Optional.ofNullable(resultEnum).orElseGet(()->ResultEnum.RESULT_FAIL);
        this.code = resultEnum.getCode();
        this.msg = Optional.ofNullable(msg).orElse(resultEnum.getMsg());
    }
}
