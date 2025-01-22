package com.xiaosi.back.handler;

import jakarta.persistence.OptimisticLockException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OptimisticLockException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleOptimisticLockException(OptimisticLockException e) {
        return "Optimistic lock exception occurred: " + e.getMessage();
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleOptimisticLockException(ObjectOptimisticLockingFailureException e) {
        return "Optimistic lock exception occurred: " + e.getMessage();
    }
}
