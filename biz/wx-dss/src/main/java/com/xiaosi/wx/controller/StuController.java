package com.xiaosi.wx.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaosi.wx.config.RsaKeyProperties;
import com.xiaosi.wx.entity.SysMenu;
import com.xiaosi.wx.mapper.SysUserMapper;
import com.xiaosi.wx.entity.SysUser;
import com.xiaosi.wx.page.BasePage;
import com.xiaosi.wx.page.mybatisplus.PageQuery;
import com.xiaosi.wx.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sgh
 * @since 2024-04-19
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/stu")
public class StuController {

    private final RsaKeyProperties rsaKeyProperties;
    private final MenuService menuService;

//    @PreAuthorize("hasPermission('USER1','READ')")
//    @PostMapping("/add")
//    public List<SysUser> getList(@RequestBody SysUser sysUser, HttpServletRequest request) throws IOException {
////        System.out.println(rsaKeyProperties.getPrivateRsaKey());
//        Resource publicKey = rsaKeyProperties.getPublicKey();
////        System.out.println(IOUtils.toString(publicKey.getInputStream(), StandardCharsets.UTF_8));
////        System.out.println(rsaKeyProperties.getPublicRsaKey());
//        return sysUserMapper.selectList(null);
//    }

    @PostMapping("/add")
    public List<SysMenu> getList(@RequestBody SysMenu sysMenu) throws IOException {
//        System.out.println(rsaKeyProperties.getPrivateRsaKey());
        Resource publicKey = rsaKeyProperties.getPublicKey();
//        System.out.println(IOUtils.toString(publicKey.getInputStream(), StandardCharsets.UTF_8));
//        System.out.println(rsaKeyProperties.getPublicRsaKey());
//        Page<SysMenu> pageList = menuService.page(query.toMpPageDefaultSortByCreateTimeDesc());
        return menuService.getPage(sysMenu);
    }

}
