package com.xiaosi.wx.easyexcel.demo1;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
public class FireChemicalDto {
    @ExcelIgnore
    private static final long serialVersionUID = 1L;


    @ExcelProperty(value = "casNo", index = 1)
    private String casNo;

    @ExcelProperty(value = "国际危险号", index = 2)
    private String dangerId;

    @ExcelProperty(value = "防护处理", index = 3)
    private String defendWay;

    @ExcelProperty(value = "处理措施", index = 4)
    private String dispose;

    @ExcelProperty(value = "英文名", index = 5)
    private String englishName;

    @ExcelProperty(value = "分子式", index = 6)
    private String formula;

    @ExcelProperty(value = "主要成分", index = 7)
    private String ingredient;

    @ExcelProperty(value = "泄漏处理", index = 8)
    private String leakWay;
    @ExcelProperty(value = "中文名", index = 0)
    private String name;

    @ExcelProperty(value = "性状", index = 9)
    private String property;

    @ExcelProperty(value = "贮藏方法", index = 10)
    private String store;

    @ExcelProperty(value = "症状", index = 11)
    private String symptom;

    @ExcelProperty(value = "禁忌物/禁忌", index = 12)
    private String tabu;
    @ExcelIgnore
    private String typeCode;

    @ExplicitConstraint(type="CHEMICALTYPE",indexNum=13,sourceClass = RoleNameExplicitConstraint.class) //动态下拉内容
    @ExcelProperty(value = "类型名称", index = 13)
    private String type;
    //    @ExplicitConstraint(indexNum=14,source = {"男","女"}) //固定下拉内容
    @ExcelProperty(value = "国标号", index = 14)
    private String un;

    @ExcelProperty(value = "化学品图片", index = 15)
    private String image;
    @ExcelIgnore
    private Date updateTime;
    @ExcelIgnore

    private String recUserName;
    @ExcelIgnore
    public static final String bigTitle= "填写须知： \n" +
            "1.第1、2行为固定结构，不可更改；以下示例行，导入前请先删除\n" +
            "2.请严格按照填写规则输入数据，不合规的数据无法成功导入";
    /**
     * 每个模板的首行高度， 换行数目+2 乘以15
     */
    public   static int getHeadHeight(){
        return  50;
    }
}
