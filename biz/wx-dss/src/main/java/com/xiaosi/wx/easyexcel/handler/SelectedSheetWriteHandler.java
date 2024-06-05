package com.xiaosi.wx.easyexcel.handler;

import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.xiaosi.wx.easyexcel.resolve.ExcelSelectedResolve;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.util.Map;

@Data
public class SelectedSheetWriteHandler implements SheetWriteHandler {



    /**
     * 构建下拉选的map
     */
    private final Map<Integer, ExcelSelectedResolve> selectedMap;

    private final int columnSelectMaxLength = 255;

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        // 这里可以对cell进行任何操作
        Sheet sheet = writeSheetHolder.getSheet();
        DataValidationHelper helper = sheet.getDataValidationHelper();
        selectedMap.forEach((k, v) -> {
            String[] source = v.getSource();
            String selectColumnName = v.getColumnName();
            // 下拉选总字符超过255,需要通过创建sheet关联方式构建下拉选
            if (StringUtils.join(source).length() > columnSelectMaxLength) {
                // 获取一个workbook
                Workbook workbook = writeWorkbookHolder.getWorkbook();
                // 定义sheet的名称
                String sheetName = selectColumnName + k;
                // 创建一个隐藏的sheet
                Sheet tmpSheet = workbook.createSheet(sheetName);
                // 循环赋值（为了防止下拉框的行数与隐藏域的行数相对应，将隐藏域加到结束行之后）
                for (int i = 0, length = source.length; i < length; i++) {
                    // i:表示你开始的行数 0表示你开始的列数
                    tmpSheet.createRow(i).createCell(0).setCellValue(source[i]);
                }
                Name category1Name = workbook.createName();
                category1Name.setNameName(sheetName);
                // $A$1:$A$N代表 以A列1行开始获取N行下拉数据
                category1Name.setRefersToFormula(sheetName + "!$A$1:$A$" + (source.length));
                // 将刚才设置的sheet引用到你的下拉列表中, (首行，末行，首列，末列)
                CellRangeAddressList addressList = new CellRangeAddressList(v.getFirstRow(), v.getLastRow(), k, k);
                DataValidationConstraint constraint = helper.createFormulaListConstraint(sheetName);
                DataValidation validation = helper.createValidation(constraint, addressList);
                // 阻止输入非下拉选项的值
                validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
                validation.setShowErrorBox(true);
                validation.setSuppressDropDownArrow(true);
                validation.createErrorBox("提示", "请输入下拉选项中的内容");
                sheet.addValidationData(validation);
            } else {
                // 设置下拉列表的行： 首行，末行，首列，末列
                CellRangeAddressList rangeList = new CellRangeAddressList(v.getFirstRow(), v.getLastRow(), k, k);
                // 设置下拉列表的值
                DataValidationConstraint constraint = helper.createExplicitListConstraint(source);
                // 设置约束
                DataValidation validation = helper.createValidation(constraint, rangeList);
                // 阻止输入非下拉选项的值
                validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
                validation.setShowErrorBox(true);
                validation.setSuppressDropDownArrow(true);
                validation.createErrorBox("提示", "请输入下拉选项中的内容");
                sheet.addValidationData(validation);
            }
        });
    }
}
