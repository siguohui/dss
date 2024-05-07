package com.xiaosi.wx.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaosi.wx.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
@DS("master")
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser selectByUserName(@Param("username") String username);
}
