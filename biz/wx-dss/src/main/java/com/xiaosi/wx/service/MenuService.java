package com.xiaosi.wx.service;

import com.xiaosi.wx.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaosi.wx.page.BasePage;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sgh
 * @since 2024-05-05 10:08:00
 */
public interface MenuService extends IService<SysMenu> {

    List<SysMenu> getPage(SysMenu sysMenu);

}
