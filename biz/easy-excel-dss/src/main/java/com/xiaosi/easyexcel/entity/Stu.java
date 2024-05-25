package com.xiaosi.easyexcel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.xiaosi.easyexcel.annotation.EnumValue;
import com.xiaosi.easyexcel.annotation.EnumValueVaild;
import com.xiaosi.easyexcel.enums.CacheContentEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stu {
    @ExcelProperty(value = "名称")
//    @EnumValue(strValues = {"四","五"}, message = "不在范围内")
    @EnumValueVaild(enumClass = CacheContentEnum.class, message = "取值不合法,参考:CacheContentEnum")
    @NotBlank(message = "名称不能为空")
    private String name;
    @Pattern(regexp = "^[1][3,4,5,7,8][0-9]{9}$$", message = "手机号不合法")
    @NotBlank(message = "手机号不能为空")
    @ExcelProperty(value = "手机号")
    private String mobile;
    @ExcelProperty(value = "日期")
//    @Pattern(regexp = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$", message = "出版日期格式(yyyy-MM-dd)有误")
//    @Pattern(regexp = "^[0-9]{4}\\\\-[0-9]{2}\\\\-[0-9]{2}$", message = "出版日期格式(yyyy-MM-dd)有误")
    private LocalDate curDate;
}
