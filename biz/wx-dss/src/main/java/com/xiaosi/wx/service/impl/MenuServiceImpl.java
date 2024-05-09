package com.xiaosi.wx.service.impl;

import com.xiaosi.wx.entity.SysMenu;
import com.xiaosi.wx.mapper.SysMenuMapper;
import com.xiaosi.wx.page.BasePage;
import com.xiaosi.wx.page.annotation.PageX;
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

    @PageX
    @Override
    public List<SysMenu> getPage(SysMenu sysMenu) {
        return list();
    }
}
