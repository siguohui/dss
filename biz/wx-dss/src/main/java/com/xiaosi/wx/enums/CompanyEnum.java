package com.xiaosi.wx.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xiaosi.wx.pojo.IResult;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 缓存内容
 */
@Getter
@AllArgsConstructor
public enum CompanyEnum implements IResult {

    CUSTOMER_CACHE(1, "百度"),

    ORDER_CACHE(2, "腾讯"),

    PRODUCT_CACHE(3, "阿里");

    @EnumValue
    private Integer code;
    @JsonValue
    private String msg;

    @Override
    public String toString() {
        return this.msg;
    }

    public static CompanyEnum getIResult(String lable) {
        return Stream.of(values())
                .filter(bean -> bean.msg.equals(lable))
                .findAny()
                .orElse(null);
    }

    public static String[] getMsgArr(){
        List<String> list = Stream.of(values()).map(m -> m.msg).collect(Collectors.toList());
        return list.stream().toArray(String[]::new);
    }
}
