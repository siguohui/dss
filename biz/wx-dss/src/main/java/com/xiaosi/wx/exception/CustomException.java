package com.xiaosi.wx.exception;

import com.xiaosi.wx.pojo.ResultEnum;
import lombok.Data;
import org.springframework.security.core.AuthenticationException;

public class CustomException extends AuthenticationException {

    private Integer code;

    public CustomException(String msg) {
        super(msg);
    }

    public CustomException(ResultEnum resultEnum){
        super(String.format("%s",resultEnum.getMsg()));
        this.code = resultEnum.getCode();
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
