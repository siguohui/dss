package com.xiaosi.wx.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.xiaosi.wx.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaosi.wx.permission.annotation.DssDataPermission;
import com.xiaosi.wx.permission.mapper.DataPermissionMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sgh
 * @since 2024-05-05 10:08:00
 */
@Mapper
@DS("master")
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    Set<SysMenu> selectMenuByRoleId(@Param("roleIds") Set<Long> roleIds);

//    @DssDataPermission
    List<SysMenu> getList();

}
