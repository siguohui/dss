package com.xiaosi.easyexcel.handler;

import com.xiaosi.easyexcel.exception.ImportException;
import com.xiaosi.easyexcel.resp.BaseResponse;
import com.xiaosi.easyexcel.resp.RespGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Log logger = LogFactory.getLog(GlobalExceptionHandler.class);

    @ExceptionHandler(ImportException.class)
    public BaseResponse<Object> businessException(ImportException importException) {
        return RespGenerator.fail(importException);
    }

    /*@ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    private Response<?> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("MethodArgumentNotValidException", e);
        FieldError fieldError = e.getBindingResult().getFieldError();
        return ResponseUtils.create(CommonCodeEnum.VALIDATE_ERROR.getCode(), CommonCodeEnum.VALIDATE_ERROR.getMessage(), fieldError.getDefaultMessage());
    }*/
}
