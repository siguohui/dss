package com.xiaosi.wx.exception;

import com.xiaosi.wx.pojo.ResultEnum;

public class ServiceException extends AbstractException{

    public ServiceException(ResultEnum resultEnum, String msg, Throwable throwable) {
        super(resultEnum, msg, throwable);
    }
    public ServiceException(ResultEnum resultEnum) {
        this(resultEnum, null, null);
    }
    public ServiceException(ResultEnum resultEnum,String msg) {
        this(resultEnum, msg, null);
    }
}
