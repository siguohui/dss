package com.xiaosi.wx.easyexcel.converter;

import com.baomidou.mybatisplus.annotation.IEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

public class EnumConverterFactory<T extends Enum<?> & IEnum<Integer>> implements ConverterFactory<String, T> {

    @Override
    public <T1 extends T> Converter<String, T1> getConverter(Class<T1> targetType) {
        return new EnumConverter<>(targetType);
    }
}
