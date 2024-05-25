package com.xiaosi.wx.easyexcel.listener;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.holder.ReadSheetHolder;
import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import com.xiaosi.wx.easyexcel.valid.EasyExcelValidateHelper;
import com.xiaosi.wx.entity.Stu;
import com.xiaosi.wx.exception.ImportException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import java.util.*;

@Slf4j
public class EasyExcelListener<T> extends AnalysisEventListener<T> {
    private final List<T> dataList = new ArrayList<>();

    List<String> errMsgList = new ArrayList<>();

    Set<String> setHead;
    public EasyExcelListener(Set<String> setHead) {
        if(Objects.isNull(setHead)){
            this.setHead = Sets.newHashSet();
        } else {
            this.setHead = setHead;
        }
    }

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {

        int rowIndex =  analysisContext.readRowHolder().getRowIndex() + 1 ;
//        int columnIndex = ((ReadSheetHolder) analysisContext.currentReadHolder()).getCellExtra().getColumnIndex() + 1;

        /*int rowIndex = ((ReadSheetHolder) analysisContext.currentReadHolder()).getRowIndex() + 1;
        Field[] declaredFields = t.getClass().getDeclaredFields();
        //Method[] declaredMethods = ReflectionUtils.getDeclaredMethods(t.getClass());
        //Field[] fields = ReflectUtil.getFields(t.getClass());
        for (Field declaredField : declaredFields) {
            if(execute == rowIndex){
                return;
            }
            declaredField.setAccessible(true);
            try {
                Object o = declaredField.get(t);
                if(Objects.nonNull(o)){
                    String content = (String)o;
                    if (content.contains("数据") && content.contains("导入")) {
                        execute = rowIndex;
                        return;
                    }
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }*/


        /*validChoiceInfo(t, analysisContext);*/
        String errorMsg = EasyExcelValidateHelper.validate(t);
        if (StrUtil.isNotEmpty(errorMsg)) {
            String msg = "第" + rowIndex + "行," + errorMsg;
            errMsgList.add(msg);
        } else {
            dataList.add(t);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("所有数据解析完成！");
        if(!errMsgList.isEmpty()){
            throw new ImportException(Joiner.on("-").join(errMsgList));
        }
        if (dataList.isEmpty()) {
            throw new ImportException("上传失败，Excel中无数据!!");
        }
        dataList.clear();
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        headMap.entrySet().removeIf(entry -> Objects.isNull(entry.getValue()));
        if(setHead.size() != headMap.size()){
            throw new ImportException("导入的模板不符合,请检查后重新导入!!");
        }
        headMap.values().forEach(f->{
            if(!setHead.contains(f)){
                throw new ImportException("导入的模板不符合,请检查后重新导入!!");
            }
        });
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.error(exception.getMessage());
        throw new ImportException(exception.getMessage());
       /* if (exception instanceof ExcelDataConvertException) {
            Integer columnIndex = ((ExcelDataConvertException) exception).getColumnIndex() + 1;
            Integer rowIndex = ((ExcelDataConvertException) exception).getRowIndex() + 1;
            String message = "第" + rowIndex + "行，第" + columnIndex + "列" + "数据格式有误，请核实";
            throw new RuntimeException(message);
        } else if (exception instanceof RuntimeException) {
            throw exception;
        } else {
            super.onException(exception, context);
        }*/
    }

    public List<T> getData() {
        return dataList;
    }

    private static<T> void validChoiceInfo(T object, AnalysisContext context) {
        // 泛型为用户
        if(object instanceof Stu) {
            //泛型转换为实体类型
            Object temp = object;
            Stu user = (Stu)temp;
            if(StringUtils.isBlank(user.getName())){
                log.info("上传失败:第{}行ID信息为空",context.readRowHolder().getRowIndex());
                throw new ImportException("上传失败:第"+context.readRowHolder().getRowIndex().toString()+"行ID信息为空");
            }
            if(StringUtils.isBlank(user.getName())){
                log.info(String.format("上传失败:第{}行用户名信息为空",context.readRowHolder().getRowIndex()));
                throw new ImportException(String.format("上传失败:第{}行用户名信息为空",context.readRowHolder().getRowIndex()));
            }
        }
    }
}
