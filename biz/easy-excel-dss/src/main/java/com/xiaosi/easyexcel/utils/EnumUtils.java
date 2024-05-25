package com.xiaosi.easyexcel.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaosi.easyexcel.enums.BaseEnum;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * 枚举工具类
 */
public final class EnumUtils {

    public static <T extends BaseEnum> T getByCode(Class<T> enumClazz, String code){
        BaseEnum[] values;

        try {
            Method method = enumClazz.getMethod("values");
            method.setAccessible(true);
            values = (BaseEnum[]) method.invoke(enumClazz);
        } catch (Exception e){
            throw new RuntimeException(e);
        }

        BaseEnum result = Stream.of(values)
                .filter(baseEnum -> baseEnum.getEnumCode().equals(code))
                .findFirst()
                .orElse(null);

        return result == null ? null : (T) result;
    }

    public static boolean isValidCode(Class<? extends BaseEnum> enumClazz, String code){
        return getByCode(enumClazz, code) != null;
    }

    public static JSONArray toJsonArray(Class<? extends BaseEnum> enumClazz){
        JSONArray array = new JSONArray();

        try {
            Method method = enumClazz.getMethod("values");
            method.setAccessible(true);
            BaseEnum[] values = (BaseEnum[]) method.invoke(enumClazz);

            Arrays.stream(values).forEach(enumValue -> {
                JSONObject json = new JSONObject();
                json.put("code", enumValue.getEnumCode());
                json.put("desc", enumValue.getEnumDesc());
                array.add(json);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return array;
    }
}
