package com.xiaosi.wx.exception;

public class ServiceException extends RuntimeException{

    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
