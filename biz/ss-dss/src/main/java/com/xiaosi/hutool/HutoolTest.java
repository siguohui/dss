package com.xiaosi.hutool;

import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import org.testng.annotations.Test;

import java.util.List;

public class HutoolTest {

    @Test
    public void test() {
        //读取Excel文件的内容-选定工作表名读取
        ExcelReader reader = ExcelUtil.getReader("D:\\1.xlsx","Sheet1");
//根据指定行开始读取所有的内容
        List<List<Object>> rows = reader.read(1, reader.getRowCount());
        System.out.println(rows);
//在获取到所有的数据后，要关闭reader(切记)，操作文件总是失败，最后才发现在service调用没有关闭流，粗心大意了！
        reader.close();
    }

    public static void main(String[] args) {
        System.out.println(UnicodeUtil.toUnicode("四国辉"));
    }
}
