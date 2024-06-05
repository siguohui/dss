package com.xiaosi.wx.easyexcel.dynamic;

import com.xiaosi.wx.easyexcel.ExcelDynamicSelect;
import com.xiaosi.wx.enums.CompanyEnum;

public class CompanySelector implements ExcelDynamicSelect {

    @Override
    public String[] getSource() {
        // todo 此处可通过获取dao实例来查询数据库,构建动态数据
        return CompanyEnum.getMsgArr();
    }
}
