package com.xiaosi.wx.mapper;

import com.xiaosi.wx.model.SysUserRole;

public interface SysUserRoleMapper {
    int deleteByPrimaryKey(SysUserRole key);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);
}
