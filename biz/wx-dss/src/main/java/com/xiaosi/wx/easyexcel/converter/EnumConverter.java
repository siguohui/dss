package com.xiaosi.wx.easyexcel.converter;

import com.baomidou.mybatisplus.annotation.IEnum;
import org.springframework.core.convert.converter.Converter;

import java.util.regex.Pattern;

public class EnumConverter<T extends Enum<?> & IEnum<Integer>> implements Converter<String, T> {
    private final Class<T> cls;


    EnumConverter(Class<T> cls) {
        this.cls = cls;
    }


    @Override
    public T convert(String source) {
        if (!isValid(source)) {
            throw new RuntimeException("不是合法的自然数");
        }
        T[] enumConstants = cls.getEnumConstants();
        Integer sourceInteger = Integer.valueOf(source);
        for (T enumConstant : enumConstants) {
            if (sourceInteger.equals(enumConstant.getValue())) {
                return enumConstant;
            }
        }
        throw new RuntimeException("无效枚举类型");
    }


    /**
     * 是合法的自然数
     */
    private static boolean isValid(String input) {
        String regex = "^(0|[1-9]\\d*)$";
        return Pattern.matches(regex, input);
    }

}
