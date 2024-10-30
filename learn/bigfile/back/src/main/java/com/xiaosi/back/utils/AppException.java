package com.xiaosi.back.utils;

import lombok.Data;

@Data
public class AppException extends RuntimeException {

    private String msg;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param msg the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public AppException(String msg) {
        super(msg);
    }
}
