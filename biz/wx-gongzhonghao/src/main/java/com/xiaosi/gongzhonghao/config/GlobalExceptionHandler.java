package com.xiaosi.gongzhonghao.config;

import com.xiaosi.gongzhonghao.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author cacok
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Result handleRunTimeException(RuntimeException e) {
        log.error("运行时异常: {}", e.getMessage(), e);
        return Result.failed(e.getMessage());
    }

    /**
     * 异常
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error("异常: {}", e.getMessage(), e);
        return Result.failed(e.getMessage());
    }

}
