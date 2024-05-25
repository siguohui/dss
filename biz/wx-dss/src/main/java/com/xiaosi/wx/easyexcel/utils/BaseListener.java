package com.xiaosi.wx.easyexcel.utils;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.holder.ReadSheetHolder;
import com.xiaosi.wx.easyexcel.annotation.ExcelPropertyCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: Gang_Wang
 * @description
 * @Date: 2022/11/29 下午4:16
 */
@Slf4j
public class BaseListener<T> implements ReadListener<T> {

    private Class<?> headClazz;


    //读取数据map形式
    private final List<Map<String, Object>> mapData = new ArrayList<>();

    //读取数据实体类泛型形式
    private final List<T> data = new ArrayList<>();

    /**非空校验map*/
    private final Map<String, Boolean> nullAbleFieldMap = new HashMap<>();

    //格式校验map
    private final Map<String, String> checkFormatFieldMap = new HashMap<>();

    //长度校验map
    private final Map<String, Integer> checkLengthFieldMap = new HashMap<>();

    //枚举值校验map
    private final Map<String, String[]> checkEnumFieldMap = new HashMap<>();

    //校验的字典数据失败集合
    private final Map<String, String> checkDictFieldMap = new HashMap<>();

    //校验的字典数据
    private Map<String, Map<String,Object>> dictMap = new HashMap<>();

    //数据校验错误信息map key:错误的行号  value:错误信息描述
    private final Map<Integer, String> errorMessageMap = new HashMap<>();


    /**
     * 获得错误信息
     * @return
     */
    public String getErrorMessageStr() {
        StringBuilder stringBuilder = new StringBuilder();
        if(getErrorMessageMap().size()>0){
            getErrorMessageMap().forEach((k,v) -> {
                stringBuilder.append("第").append(k).append("行:").append(v);
            });
        }
        return stringBuilder.toString();
    }



    public List<Map<String, Object>> getMapData() {
        return mapData;
    }

    public List<T> getData() {
        return data;
    }

    public Map<Integer, String> getErrorMessageMap() {
        return errorMessageMap;
    }

    public BaseListener() {
    }

    /**
     * @param headClazz excel model 类对象
     */
    public BaseListener(Class<?> headClazz) {
        this.headClazz = headClazz;
    }

    public BaseListener(Class<?> headClazz,Map<String, Map<String,Object>> dictMap ) {
        this.headClazz = headClazz;
        this.dictMap = dictMap;
    }

    /**
     * 读取发生异常时的方法
     *
     * @param exception
     * @param context
     * @throws Exception
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
            log.error("第{}行，第{}列解析异常，数据为:{}", excelDataConvertException.getRowIndex() + 1,
                    excelDataConvertException.getColumnIndex(), excelDataConvertException.getCellData());
            errorMessageMap.put(excelDataConvertException.getRowIndex()+1, "第"+ (excelDataConvertException.getColumnIndex()+1) +"列解析异常");
        }
    }


    /**
     * 读取头信息
     *
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        ExcelPropertyCheck clazzHeadAnno = this.headClazz.getAnnotation(ExcelPropertyCheck.class);
        Field[] declaredFields = headClazz.getDeclaredFields();
        if (clazzHeadAnno != null && clazzHeadAnno.required()) {
            for (Field declaredField : declaredFields) {
                nullAbleFieldMap.put(declaredField.getName(), true);
            }
        }

        for (Field declaredField : declaredFields) {
            ExcelPropertyCheck annotation = declaredField.getAnnotation(ExcelPropertyCheck.class);
            if (annotation != null) {
                if (annotation.checkFormat()) {
                    checkFormatFieldMap.put(declaredField.getName(), annotation.type() + "");
                }
                if(annotation.checkDict()){
                    checkDictFieldMap.put(declaredField.getName(), "");
                }
                if (annotation.required()) {
                    nullAbleFieldMap.put(declaredField.getName(), true);
                } else {
                    nullAbleFieldMap.remove(declaredField.getName());
                }
                if (annotation.required() && annotation.length() != -1) {
                    checkLengthFieldMap.put(declaredField.getName(), annotation.length());
                }
                /*if (annotation.required() && annotation.value().length != 0) {
                    checkEnumFieldMap.put(declaredField.getName(), annotation.value());
                }*/
            }
        }
    }


    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        int rowIndex = ((ReadSheetHolder) analysisContext.currentReadHolder()).getRowIndex() + 1;
        try {
            if(checkFieldAllNull(t)){
                return ;
            }
        } catch (IllegalAccessException e) {
            log.error("判断导入为空报错");
        }
        StringBuilder error = new StringBuilder();
        Field[] declaredFields = t.getClass().getDeclaredFields();
        //必填校验和格式校验
        for (Field declaredField : declaredFields) {
            try {
                declaredField.setAccessible(true);
                if (nullAbleFieldMap.get(declaredField.getName()) != null) {
                    if (nullAbleFieldMap.get(declaredField.getName())) {
                        Object o = declaredField.get(t);
                        if (!Objects.nonNull(o)) {
                            error.append(declaredField.getAnnotation(ExcelProperty.class).value()[0]).append("为空;");
                        } else {
                            //字段不为空进行长度校验
                            if (checkLengthFieldMap.get(declaredField.getName()) != null) {
                                if (String.valueOf(o).length() > checkLengthFieldMap.get(declaredField.getName())) {
                                    error.append(declaredField.getAnnotation(ExcelProperty.class).value()[0]).append("长度错误;");
                                }
                            }
                            if (checkEnumFieldMap.get(declaredField.getName()) != null) {
                                if (Integer.parseInt(String.valueOf(o)) == -1) {
                                    error.append(declaredField.getAnnotation(ExcelProperty.class).value()[0]).append("枚举值错误;");
                                }
                            }
                        }
                    }
                }
                //是否需要进行格式校验
                if (checkFormatFieldMap.get(declaredField.getName()) != null) {
                    String res = check(String.valueOf(declaredField.get(t)), Integer.valueOf(checkFormatFieldMap.get(declaredField.getName())));
                    if (StringUtils.hasText(res)) {
                        error.append(res);
                    }
                }
                if(true ==declaredField.getAnnotation(ExcelPropertyCheck.class).checkDict()
                        && StringUtils.hasText(declaredField.getAnnotation(ExcelPropertyCheck.class).dictName())){
                    Object checkValue = checkDict(declaredField.getAnnotation(ExcelPropertyCheck.class).dictName(), (String) declaredField.get(t));
                    if(Objects.isNull(checkValue)){
                        error.append(declaredField.getAnnotation(ExcelProperty.class).value()[0]).append("没有对应字典项;");
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (StringUtils.hasText(error.toString())) {
            errorMessageMap.put(rowIndex, error.toString());
        }
        mapData.add(BeanUtil.beanToMap(t));
        data.add(t);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("excel解析完毕,共解析{}条数据,错误数据{}条，错误详情{}", data.size(), errorMessageMap.size(), errorMessageMap);
    }


    private String check(String str, Integer checkType) {
        if(StringUtils.isEmpty(str) || "null".equals(str)){
            return "";
        }
        switch (checkType) {
            case 0:
                return isValidDate(str,"yyyy-MM-dd") ? "" : "日期格式错误;";
            case 1:
                return isValidDate(str,"HH:mm") ? "" : "日期格式错误;";
            default:
                return "";
        }
    }

    /**
     * 获得对应的字典值
     * @param dictType 字典类型
     * @param dictName 字典文字
     * @return
     */
    public Object checkDict(String dictType,String dictName){

        Map<String,Object> dictInfoMap = dictMap.get(dictType);
        if(null == dictInfoMap){
            return null;
        }
        Object dictValue = dictInfoMap.get(dictName);
        return dictValue;
    }

    /**
     * 检测一个字符串是否是时间格式
     * @param str
     * @return
     */
    public static boolean isValidDate(String str, String pattern) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        //SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try {
            format.setLenient(false);
            format.parse(str);
        } catch (Exception e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }


    public static boolean checkFieldAllNull(Object object) throws IllegalAccessException {
        for (Field f : object.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            if (Modifier.isFinal(f.getModifiers()) && Modifier.isStatic(f.getModifiers())) {
                continue;
            }
            if (!isEmpty(f.get(object))) {
                return false;
            }
            f.setAccessible(false);
        }
        //父类public属性
        for (Field f : object.getClass().getFields()) {
            f.setAccessible(true);
            if (Modifier.isFinal(f.getModifiers()) && Modifier.isStatic(f.getModifiers())) {
                continue;
            }
            if (!isEmpty(f.get(object))) {
                return false;
            }
            f.setAccessible(false);
        }
        return true;
    }

    private static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof String && ("").equals(object.toString())) {
            return true;
        }
        if (object instanceof Collection && ((Collection) object).isEmpty()) {
            return true;
        }
        if (object instanceof Map && ((Map) object).isEmpty()) {
            return true;
        }
        if (object instanceof Object[] && ((Object[]) object).length == 0) {
            return true;
        }
        return false;
    }
}
