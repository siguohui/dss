package com.xiaosi.wx.easyexcel.demo1;



import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;

/**
 * excel通用单元格格式类
 */
public class TemplateCellWriteHandler implements CellWriteHandler {

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        Workbook workbooks = writeSheetHolder.getSheet().getWorkbook();
        writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), 20 * 256);
        CellStyle cellStyle = workbooks.createCellStyle();
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);//设置前景填充样式
        cellStyle.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());//前景填充色
        Font font1 = workbooks.createFont();//设置字体
        font1.setBold(true);
        font1.setColor((short)1);
        font1.setFontHeightInPoints((short)15);
        cellStyle.setFont(font1);
        cell.setCellStyle(cellStyle);
        //其他列
        if (!isHead){
            CellStyle style = workbooks.createCellStyle();
            DataFormat dataFormat = workbooks.createDataFormat();
            style.setDataFormat(dataFormat.getFormat("@"));
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setAlignment(HorizontalAlignment.CENTER);
            cell.setCellStyle(style);
        }
        //设置日期
        if (!isHead && cell.getColumnIndex()==19 || !isHead && cell.getColumnIndex()==21|| !isHead && cell.getColumnIndex()==20){
            CellStyle style = workbooks.createCellStyle();
            DataFormat dataFormat = workbooks.createDataFormat();
            style.setDataFormat(dataFormat.getFormat("yyyy/mm/dd hh:mm:ss"));
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setAlignment(HorizontalAlignment.CENTER);
            cell.setCellStyle(style);
        }
        //设置金额
        if (!isHead && cell.getColumnIndex()==15 ||!isHead && cell.getColumnIndex()==16||!isHead && cell.getColumnIndex()==22 ||!isHead && cell.getColumnIndex()==24||!isHead && cell.getColumnIndex()==25){
            CellStyle style = workbooks.createCellStyle();
            DataFormat dataFormat = workbooks.createDataFormat();
            style.setDataFormat(dataFormat.getFormat("0.00"));
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setAlignment(HorizontalAlignment.CENTER);
            cell.setCellStyle(style);
        }
    }
}
