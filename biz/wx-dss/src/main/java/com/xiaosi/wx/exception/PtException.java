package com.xiaosi.wx.exception;

public class PtException extends RuntimeException{

    public PtException(String msg) {
        super(msg);
    }

    public PtException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
