package com.xiaosi.wx.utils.tree;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ModuleType {

    EMOS("EMOS","D:\\sgh\\other\\workspace\\idea\\boya\\Source\\boya-science-emos\\src\\main\\resources\\mappings"),
    SYS("SYS","D:\\sgh\\other\\workspace\\idea\\boya\\Source\\boya-science-system\\src\\main\\resources\\mappings"),
    I18N("I18N","D:\\sgh\\other\\workspace\\idea\\boya\\Source\\boya-science-i18n\\boya-science-i18n-biz\\src\\main\\resources\\mappings\\i18n");

    private String module;
    private String path;
}
