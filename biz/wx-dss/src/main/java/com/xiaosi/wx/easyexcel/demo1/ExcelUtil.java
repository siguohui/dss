package com.xiaosi.wx.easyexcel.demo1;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;

public class ExcelUtil {
    /**
     * 生成excel模板
     *
     * @param response
     * @param fileName    下载的文件名，
     * @param sheetName   sheet名
     * @param data        导出的数据
     * @param model       导出的头
     * @param heardHeight 头行高
     */
    public static void createTemplate(HttpServletResponse response, String fileName,
                                      String sheetName, List<? extends Object> data,
                                      Class<?> model, int heardHeight)  {

        HorizontalCellStyleStrategy horizontalCellStyleStrategy = setMyCellStyle();
        try {
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
            EasyExcel.write(getOutputStream(fileName, response, ExcelTypeEnum.XLSX), model).
                    excelType(ExcelTypeEnum.XLSX).sheet(sheetName)
                    .registerWriteHandler(new TemplateCellWriteHandlerDate(heardHeight,explicitListConstraintMap))
                    .registerWriteHandler(new TemplateCellWriteHandler())
                    .registerWriteHandler(horizontalCellStyleStrategy)
                    .doWrite(data);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("系统异常！");

        }
    }


    /**
     * 解析注解内容 获取下列表信息
     * @param explicitConstraint
     * @return
     */
    public static Map<Integer, String[]> resolveExplicitConstraint(  Map<Integer, String[]> explicitListConstraintMap,ExplicitConstraint explicitConstraint){
        if (explicitConstraint == null) {
            return null;
        }
        //固定下拉信息
        String[] source = explicitConstraint.source();
        if (source.length > 0) {
            explicitListConstraintMap.put(explicitConstraint.indexNum(), source);
        }
        //动态下拉信息
        Class<? extends ExplicitInterface>[] classes = explicitConstraint.sourceClass();
        if (classes.length>0){
            ExplicitInterface explicitInterface = null;
            try {
                explicitInterface = classes[0].newInstance();
                String[] source1 = explicitInterface.source(explicitConstraint.type());
                if (source1.length>0){
                    explicitListConstraintMap.put(explicitConstraint.indexNum(), source1);
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }




    /**
     * 导出文件时为Writer生成OutputStream
     */
    private static OutputStream getOutputStream(String fileName, HttpServletResponse response, ExcelTypeEnum excelTypeEnum) throws Exception {
        //创建本地文件
        String filePath = fileName + excelTypeEnum.getValue();
        try {
            fileName = new String(filePath.getBytes(), StandardCharsets.ISO_8859_1);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "filename=" + fileName);
            return response.getOutputStream();
        } catch (IOException e) {
            throw new Exception("系统异常");
        }
    }

    /**
     * 创建我的cell  策略
     *
     * @return
     */
    public static HorizontalCellStyleStrategy setMyCellStyle() {

        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 设置表头居中对齐
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 颜色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 10);
        // 字体
        headWriteCellStyle.setWriteFont(headWriteFont);
        headWriteCellStyle.setWrapped(true);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 设置内容靠中对齐
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        return horizontalCellStyleStrategy;
    }
}
