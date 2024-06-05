package com.xiaosi.wx.easyexcel.demo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ExcelVo {

    @ExplicitConstraint(source={"男","女"},indexNum = 11)
    @ExcelProperty(value = {"性别"}, index = 11)
    private String sex;
}
