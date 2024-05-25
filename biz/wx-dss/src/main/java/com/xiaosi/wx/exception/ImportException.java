package com.xiaosi.wx.exception;

import com.xiaosi.wx.pojo.ResultEnum;

public class ImportException extends AbstractException {
    public ImportException(ResultEnum resultEnum, String msg, Throwable throwable) {
        super(resultEnum, msg, throwable);
    }
    public ImportException(ResultEnum resultEnum) {
        this(resultEnum, null, null);
    }

    public ImportException(String msg) {
        this(null, msg, null);
    }

    public ImportException(ResultEnum resultEnum,String msg) {
        this(resultEnum, msg, null);
    }

    public ImportException() {
        super(null,null,null);
    }
}
