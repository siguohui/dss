package com.xiaosi.wx.easyexcel.model;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.google.common.collect.Lists;
import com.xiaosi.wx.easyexcel.annotation.ExcelSelected;
import com.xiaosi.wx.easyexcel.dynamic.CompanySelector;
import com.xiaosi.wx.easyexcel.handler.SelectedSheetWriteHandler;
import com.xiaosi.wx.easyexcel.handler.SelectedSheetWriteHandler1;
import com.xiaosi.wx.easyexcel.handler.XdxCellWriteHandler;
import com.xiaosi.wx.easyexcel.resolve.ExcelSelectedResolve;
import com.xiaosi.wx.easyexcel.utils.EasyExcelUtil;
import com.xiaosi.wx.enums.CompanyEnum;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;

@Data
@HeadRowHeight(value = 25) // 头部行高
@ContentRowHeight(value = 30) // 内容行高
@HeadStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER,fillBackgroundColor = 13) // 表头样式
@HeadFontStyle(fontName = "黑体")
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER) // 内容样式
public class ExcelModel {

    @ColumnWidth(30)
    @ExcelSelected(sourceClass = CompanySelector.class)
    @ExcelProperty("公司")
    private CompanyEnum company;

    @SneakyThrows
    public static void main(String[] args) {
        File file = new File("D:\\sgh\\test.xlsx");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            Map<Integer, ExcelSelectedResolve> excelCellSelectMap = EasyExcelUtil.resolveSelectedAnnotation(ExcelModel.class);
            EasyExcel.write(fileOutputStream, ExcelModel.class)
                    .sheet("sheet1")
                    .registerWriteHandler(new XdxCellWriteHandler(excelCellSelectMap))
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .registerWriteHandler(getHeightAndFontStrategy())
                    .doWrite(Lists.newArrayList());

//            File f = new File("D:\\sgh\\关键部件Sbom模板.xlsx");
//            FileInputStream f2  = new FileInputStream(f);
//
//            ExcelWriter excelWriter = EasyExcel.write(fileOutputStream)
//                    .withTemplate(f2)
//                    .excelType(ExcelTypeEnum.XLSX).registerWriteHandler(new XdxCellWriteHandler(excelCellSelectMap)).build();
//            WriteSheet sheet = EasyExcel.writerSheet().registerWriteHandler().build();
//            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE)
//                    .build();
//            excelWriter.fill(Lists.newArrayList(), sheet);
//            excelWriter.finish();
//            fileOutputStream.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *导出策略
     */
    public static HorizontalCellStyleStrategy getHeightAndFontStrategy() {
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 11);
        headWriteFont.setBold(true);
        headWriteCellStyle.setWriteFont(headWriteFont);
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontHeightInPoints((short) 11);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }

}
