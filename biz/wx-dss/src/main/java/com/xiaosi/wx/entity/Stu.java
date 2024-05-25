package com.xiaosi.wx.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaosi.wx.base.BaseEntity;

import com.xiaosi.wx.easyexcel.annotation.CheckValid;
import com.xiaosi.wx.easyexcel.converter.LevelEnumConverter;
import com.xiaosi.wx.enums.LevelEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author sgh
 * @since 2024-05-11 06:07:12
 */
@Getter
@Setter
//@Accessors(chain = true)
@TableName("stu")
@Schema(name = "Stu", description = "")
@NoArgsConstructor
@AllArgsConstructor
public class Stu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("name")
    @ExcelProperty(value = "名称")
    @NotBlank(message = "等级不能为空")
    private String name;

    @ExcelProperty(value = "等级", converter = LevelEnumConverter.class)
//    @CheckValid(strValues = {"四","五"}, message = "不在范围内")
    @CheckValid(enumClass = LevelEnum.class, message = "取值不合法")
//    @NotBlank(message = "等级不能为空")
    /*@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString) 如果springmvc中json转换改成fastjson，需要此注解*/
    private LevelEnum level;

    /*@Pattern(regexp = "^[1][3,4,5,7,8][0-9]{9}$$", message = "手机号不合法")
    @NotBlank(message = "手机号不能为空")
    @ExcelProperty(value = "手机号")
    private String mobile;*/
    /*@ExcelProperty(value = "日期")
    @Pattern(regexp = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$", message = "出版日期格式(yyyy-MM-dd)有误")
    @Pattern(regexp = "^[0-9]{4}\\\\-[0-9]{2}\\\\-[0-9]{2}$", message = "出版日期格式(yyyy-MM-dd)有误")
    private LocalDate curDate;*/
}
