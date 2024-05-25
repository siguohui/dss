package com.xiaosi.easyexcel.resp;

import com.xiaosi.easyexcel.exception.ImportException;

public class RespGenerator {

    public static BaseResponse<Object> fail(ImportException importException) {
        return BaseResponse.build(importException.getCode(),importException.getMessage(),null);
    }
}
