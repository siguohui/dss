package com.xiaosi.wx.easyexcel.demo;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author:
 * @createDate: 2021/7/29
 * @version: 1.0
 */
public class DemoUtils {
    public static void main(String[] args) {


        String fileName="D:\\sgh\\55.xlsx";

        createTemplate(fileName,"demo001",ExcelVo.class,
                "111111",30,11);




    }



    public  static  void createTemplate(String fileName,
                                        String sheetName,
                                        Class<?> model, String title,int heardHeight,int cellIndex){

        //下拉列表集合
        Map<Integer, String[]> explicitListConstraintMap = new HashMap<>();
        //循环获取对应列得下拉列表信息
        Field[] declaredFields = model.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            //解析注解信息
            ExplicitConstraint explicitConstraint = field.getAnnotation(ExplicitConstraint.class);
            resolveExplicitConstraint(explicitListConstraintMap,explicitConstraint);
        }

        EasyExcel.write(fileName)
                .excelType(ExcelTypeEnum.XLSX).sheet(sheetName)
                .registerWriteHandler(new CreateTemplateWriteHandler(title, heardHeight, cellIndex,explicitListConstraintMap))
                .head(model)
                .useDefaultStyle(true).relativeHeadRowIndex(1)
                .doWrite(Lists.newArrayList());



    }

    /**
     * 解析注解内容 获取下列表信息
     * @param explicitConstraint
     * @return
     */
    public static Map<Integer, String[]> resolveExplicitConstraint(Map<Integer, String[]> explicitListConstraintMap, ExplicitConstraint explicitConstraint){
        if (explicitConstraint == null) {
            return null;
        }
        //固定下拉信息
        String[] source = explicitConstraint.source();
        if (source.length > 0) {
            explicitListConstraintMap.put(explicitConstraint.indexNum(), source);
        }

        return explicitListConstraintMap;
    }



}
