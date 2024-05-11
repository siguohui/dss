package com.xiaosi.wx.permission.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@AllArgsConstructor
@Getter
public enum DataPermission {

    // 枚举类型根据范围从前往后排列，避免影响getScope
    // DATA_SCOPE_ALL、DATA_SCOPE_CUSTOM、DATA_SCOPE_DEPT、DATA_SCOPE_DEPT_AND_CHILD、DATA_SCOPE_SELF
    // Scope 数据权限范围 ：ALL（全部）、DEPT（部门）、MYSELF（自己）
    DATA_MANAGER("数据管理员", "DATA_MANAGER",DataScope.ALL),
    DATA_AUDITOR("数据审核员", "DATA_AUDITOR",DataScope.DEPT),
    DATA_OPERATOR("数据业务员", "DATA_OPERATOR",DataScope.MYSELF);

    private String name;
    private String code;
    private DataScope scope;


    public static String getName(String code) {
        for (DataPermission type : DataPermission.values()) {
            if (type.getCode().equals(code)) {
                return type.getName();
            }
        }
        return null;
    }

    public static String getCode(String name) {
        for (DataPermission type : DataPermission.values()) {
            if (type.getName().equals(name)) {
                return type.getCode();
            }
        }
        return null;
    }

    public static DataScope getScope(Collection<String> code) {
        for (DataPermission type : DataPermission.values()) {
            for (String v : code) {
                if (type.getCode().equals(v)) {
                    return type.getScope();
                }
            }
        }
        return DataScope.MYSELF;
    }
}
