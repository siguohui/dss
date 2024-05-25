package com.xiaosi.easyexcel.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class BaseResponse<T> {
    private String code;
    private String message;
    private T data;
}
