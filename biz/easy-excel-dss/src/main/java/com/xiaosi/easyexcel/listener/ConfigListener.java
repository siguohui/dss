package com.xiaosi.easyexcel.listener;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import com.xiaosi.easyexcel.entity.Stu;
import com.xiaosi.easyexcel.exception.ImportException;
import com.xiaosi.easyexcel.service.StuService;
import com.xiaosi.easyexcel.utils.EasyExcelValidateHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.*;

@Slf4j
public class ConfigListener<T> extends AnalysisEventListener<T> {
    private final List<T> dataList = new ArrayList<>();

    private StuService stuService;
    public ConfigListener(StuService stuService) {
        this.stuService = stuService;
    }

    List<String> errMsgList = new ArrayList<>();

    Set<String> setHead = Sets.newHashSet("名称", "手机号", "日期");

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
//        validChoiceInfo(t, analysisContext);
        String errorMsg = EasyExcelValidateHelper.validate(t);
        if (StrUtil.isNotEmpty(errorMsg)) {
            errMsgList.add(errorMsg);
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
