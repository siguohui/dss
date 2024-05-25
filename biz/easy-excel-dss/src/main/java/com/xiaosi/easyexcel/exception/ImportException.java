package com.xiaosi.easyexcel.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ImportException extends RuntimeException {
    private String msg;
    private String code;

    public ImportException() {
        super();
    }

    public ImportException(String code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public ImportException(String message) {
        super(message);
        this.msg = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return msg;
    }
}
