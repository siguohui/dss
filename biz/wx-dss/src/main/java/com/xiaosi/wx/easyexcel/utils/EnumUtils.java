package com.xiaosi.wx.easyexcel.utils;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaosi.wx.enums.LevelEnum;
import com.xiaosi.wx.pojo.IResult;
import lombok.SneakyThrows;
import lombok.val;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * 枚举工具类
 */
public final class EnumUtils {

    @SneakyThrows
    public static <T extends IResult> T getByCode(Class<T> enumClazz, String code){
        Method method = ReflectUtil.getMethodByName(enumClazz, "values");
        IResult[] values = (IResult[]) method.invoke(enumClazz);
        IResult result = Stream.of(values)
                .filter(baseEnum -> baseEnum.getMsg().equals(code))
                .findFirst()
                .orElse(null);
        return result == null ? null : (T) result;
    }

    public static boolean isValidCode(Class<? extends IResult> enumClazz, String code){
        return getByCode(enumClazz, code) != null;
    }

    public static JSONArray toJsonArray(Class<? extends IResult> enumClazz){
        JSONArray array = new JSONArray();

        try {
            Method method = enumClazz.getMethod("values");
            method.setAccessible(true);
            IResult[] values = (IResult[]) method.invoke(enumClazz);

            Arrays.stream(values).forEach(enumValue -> {
                JSONObject json = new JSONObject();
                json.put("code", enumValue.getCode());
                json.put("msg", enumValue.getMsg());
                array.add(json);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return array;
    }
}
