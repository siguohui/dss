package com.xiaosi.wx.easyexcel.demo;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * 创建模板
 * @author：
 * @date： 2020/11/30 13:48
 * @description： TODO
 */
public class CreateTemplateWriteHandler implements SheetWriteHandler {


    /**
     * 第一行内容
     */
    private String firstTitle;


    /**
     * 实体模板类的行高
     */
    private int height;


    /**
     * 实体类 最大的列坐标 从0开始算
     */
    private int  lastCellIndex;



    private Map<Integer, String[]> explicitListConstraintMap = new HashMap<>();

    public CreateTemplateWriteHandler(String firstTitle, int height, int cellCounts,Map<Integer, String[]> explicitListConstraintMap) {
        this.firstTitle = firstTitle;
        this.height = height;
        this.lastCellIndex = cellCounts;
        this.explicitListConstraintMap = explicitListConstraintMap;

    }

    public CreateTemplateWriteHandler(String firstTitle, int height, int cellCounts) {
        this.firstTitle = firstTitle;
        this.height = height;
        this.lastCellIndex = cellCounts;


    }

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

    }


    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        Workbook workbook = writeWorkbookHolder.getWorkbook();
        Sheet sheet = workbook.getSheetAt(0);




        DataValidationHelper helper = sheet.getDataValidationHelper();

        // k 为存在下拉数据集的单元格下表 v为下拉数据集
        explicitListConstraintMap.forEach((k, v) -> {
            // 设置下拉单元格的首行 末行 首列 末列   【因为我的业务是第一行是描述 ，第二行是列头，第三行是内容  所以入参下标从2开始，暂定5000行，可以写最大行，也可以根据业务而定】
            CellRangeAddressList rangeList = new CellRangeAddressList(2, 5000, k, k);
            // 下拉列表约束数据
            DataValidationConstraint constraint = helper.createExplicitListConstraint(v);
            // 设置约束
            DataValidation validation = helper.createValidation(constraint, rangeList);
            // 阻止输入非下拉选项的值
            validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            validation.setShowErrorBox(true);
            validation.setSuppressDropDownArrow(true);
            validation.createErrorBox("提示", "此值与单元格定义格式不一致");
            sheet.addValidationData(validation);
        });

//----------和之前的逻辑一样

        Row row1 = sheet.createRow(0);
        row1.setHeight((short) height);
        //字体样式
        Font font = workbook.createFont();
        font.setColor((short)2);
        Cell cell = row1.createCell(0);

        //单元格样式
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
        cell.setCellStyle(cellStyle);

        //设置单元格内容
        cell.setCellValue(firstTitle);


        //合并单元格  --> 起始行， 终止行   ，起始列，终止列
        sheet.addMergedRegionUnsafe(new CellRangeAddress(0, 0, 0, lastCellIndex));


    }
}
