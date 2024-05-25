package com.xiaosi.wx.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xiaosi.wx.pojo.IResult;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum LevelEnum implements IResult {

    ONE(1, "L1"),
    TWO(2, "L2");

    @EnumValue
    public final Integer code;
    @JsonValue
    public final String msg;
    @Override
    public String toString() {
        return this.msg;
    }

    public static LevelEnum getIResult1(String lable) {
        return Stream.of(values())
                .filter(bean -> bean.msg.equals(lable))
                .findAny()
                .orElse(null);
    }
}
