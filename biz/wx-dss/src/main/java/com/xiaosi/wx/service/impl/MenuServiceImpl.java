package com.xiaosi.wx.service.impl;

import com.xiaosi.wx.entity.SysMenu;
import com.xiaosi.wx.mapper.SysMenuMapper;
import com.xiaosi.wx.permission.annotation.DssDataPermission;
import com.xiaosi.wx.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sgh
 * @since 2024-05-05 10:08:00
 */
@Service
public class MenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements MenuService {

    @Override
    public List<SysMenu> getList() {
        return baseMapper.getList();
    }
}
