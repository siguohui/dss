package org.xiaosi.stomp.work.util;

import cn.hutool.core.util.StrUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description: kcaco的工具类
 *
 * @author kcaco
 * @since 2022-08-20 17:02
 */
public class KcacoUtil {


    /**
     * 获取对象的<属性名, 属性值>map
     *
     * @param clazz T对象的class
     * @param t     对象示例
     * @param <T>   泛型
     * @return map<属性名, 属性值>
     */
    public static <T> Map<String, String> getObjectFieldValueMap(Class<T> clazz, T t) {
        Field[] fields = clazz.getDeclaredFields();
        List<Field> fieldList = Arrays.stream(fields).collect(Collectors.toList());

        Map<String, String> fieldsValueMap = new HashMap<>();
        for (Field field : fieldList) {
            String fieldName = field.getName();
            field.setAccessible(true);
            try {
                String fieldValue = (String) field.get(t);
                fieldsValueMap.put(fieldName, StrUtil.isBlank(fieldValue) ? "" : fieldValue);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return fieldsValueMap;
    }

}
