package com.xiaosi.wx.utils.tree;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class ExcelCountVo {

    @ExcelProperty("模块")
    private String fileName;

    @ExcelProperty("数量")
    private int count;
}
