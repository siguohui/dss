package com.xiaosi.wx.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaosi.wx.model.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
@DS("master")
public interface SysRoleMapper extends BaseMapper<SysRole> {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    List<SysRole> selectByUserId(Long userId);
}
