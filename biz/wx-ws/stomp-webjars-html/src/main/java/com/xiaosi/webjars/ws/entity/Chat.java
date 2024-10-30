package com.xiaosi.webjars.ws.entity;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Chat {

    private String to;
    private String from;
    private String content;
    @Pattern(regexp = "^(\\d+(\\.\\d+)?)$", message = "价格格式不正确")
    private BigDecimal price;
}
