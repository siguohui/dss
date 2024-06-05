package com.xiaosi.wx.easyexcel.utils;

import com.alibaba.excel.annotation.ExcelProperty;
import com.google.common.collect.Maps;
import com.xiaosi.wx.easyexcel.annotation.ExcelSelected;
import com.xiaosi.wx.easyexcel.resolve.ExcelSelectedResolve;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Map;

public class EasyExcelUtil {


    /**
     * 解析表头类中的下拉注解
     * @param head 表头类
     * @param <T> 泛型
     * @return Map<下拉框列索引, 下拉框内容> map
     */
    public static <T> Map<Integer, ExcelSelectedResolve> resolveSelectedAnnotation(Class<T> head) {
        Map<Integer, ExcelSelectedResolve> selectedMap = Maps.newHashMap();
        Field[] fields = head.getDeclaredFields();
        for (int i = 0; i < fields.length; i++){
            Field field = fields[i];
            // 解析注解信息
            ExcelSelected selected = field.getAnnotation(ExcelSelected.class);
            ExcelProperty property = field.getAnnotation(ExcelProperty.class);
            if (selected != null) {
                ExcelSelectedResolve excelSelectedResolve = new ExcelSelectedResolve();
                excelSelectedResolve.setColumnName(StringUtils.join(property.value(), "-"));
                // 处理下拉框内容
                String[] source = excelSelectedResolve.resolveSelectedSource(selected);
                if (source != null && source.length > 0){
                    excelSelectedResolve.setSource(source);
                    excelSelectedResolve.setFirstRow(selected.firstRow());
                    excelSelectedResolve.setLastRow(selected.lastRow());
                    if (property.index() >= 0){
                        selectedMap.put(property.index(), excelSelectedResolve);
                    } else {
                        selectedMap.put(i, excelSelectedResolve);
                    }
                }
            }
        }
        return selectedMap;
    }
}
