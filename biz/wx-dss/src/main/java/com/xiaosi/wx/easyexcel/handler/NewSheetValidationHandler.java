package com.xiaosi.wx.easyexcel.handler;


import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.context.SheetWriteHandlerContext;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.util.ArrayList;
import java.util.List;

/**
 * Excel单元格下拉框处理器
 * @author lc
 */
public class NewSheetValidationHandler implements SheetWriteHandler {

    /**
     * 列索引，从0开始
     */
    private final int colIndex;

    /**
     * 单元格校验值列表
     */
    private final List<String> constraints;

    /**
     * 单元格约束检测开始行，默认1 第二行开始
     */
    private int startRowIndex = 1;

    /**
     * Excel sheet最大行数，默认65536
     */
    private int endRowIndex = 65536;

    public NewSheetValidationHandler(int colIndex, List<String> constraints) {
        this.colIndex = colIndex;
        if(null == constraints){
            constraints = new ArrayList<>(0);
        }
        this.constraints = constraints;
    }
    @Override
    public void afterSheetCreate(SheetWriteHandlerContext context) {

        Workbook workbook = context.getWriteWorkbookHolder().getWorkbook();
        // 创建一个UUID
        String hiddenSheetName = "hiddenSheet" + System.currentTimeMillis();
        Sheet sheet = context.getWriteSheetHolder().getSheet();
//        sheet.getDataValidationHelper()
        // 生成下拉数据sheet页
        generateDropDownDataSheet(workbook, hiddenSheetName, constraints);
        // 设置数据验证也就是添加下拉列表
        String classFormula = hiddenSheetName + "!$A$1:$A$" + constraints.size();
        DataValidation classDataValidation = setDataValidation(workbook, hiddenSheetName, classFormula, startRowIndex, endRowIndex, colIndex, colIndex);
        sheet.addValidationData(classDataValidation);
    }



    /**
     * 功能描述: 创建Excel模板下拉列表值存储工作表并设置值
     *
     * @param wb   工作簿
     * @param sheetName 字典表sheet页名称
     * @param list 下拉框数据
     */
    public static void generateDropDownDataSheet(Workbook wb, String sheetName, List<String> list) {
        Sheet sheet = wb.createSheet(sheetName);
        //遍历下拉数据并添加至该sheet页
        for (int i = 0; i < list.size(); i++) {
            //设置列宽
            sheet.setDefaultColumnWidth(4000);
            Row row = sheet.createRow(i);
            Cell cell = row.createCell(0);
            cell.setCellValue(list.get(i));
        }
        //设置隐藏sheet页
        wb.setSheetHidden(wb.getSheetIndex(sheetName), true);
    }


    /**
     * 功能描述: 绑定下拉列表数据
     *
     * @param wb        工作簿
     * @param sheetName 字典表sheet页名称
     * @param formula   P公式
     * @param firstRow  起始行
     * @param endRow    结束行
     * @param firstCol  起始列
     * @param endCol    结束列
     * @return org.apache.poi.ss.usermodel.DataValidation
     * <p>
     * String formula = "orgInfo!$A$1:$A$59"
     * 表示orgInfo工作表A列1-59行作为下拉列表来源数据
     */
    public static DataValidation setDataValidation(Workbook wb, String sheetName, String formula, int firstRow, int endRow, int firstCol, int endCol) {
        //获取下拉框数据来源sheet
        Sheet sheet = wb.getSheet(sheetName);
        //指定设置下拉框的单元格范围
        CellRangeAddressList cellRangeAddress = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        //创建数据验证助手
        DataValidationHelper dvHelper = sheet.getDataValidationHelper();
        //创建公式列表约束
        DataValidationConstraint constraint = dvHelper.createFormulaListConstraint(formula);
        //创建验证 并返回
        return dvHelper.createValidation(constraint, cellRangeAddress);
    }
}
