package com.xiaosi.wx.permission.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DataScope {
    // Scope 数据权限范围 ：ALL（全部）、DEPT（部门）、MYSELF（自己）
    ALL("ALL"),
    DEPT("DEPT"),
    MYSELF("MYSELF");
    private String name;
}
