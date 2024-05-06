package com.xiaosi.wx.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaosi.wx.annotation.PageX;
import com.xiaosi.wx.config.RsaKeyProperties;
import com.xiaosi.wx.entity.Stu;
import com.xiaosi.wx.mapper.StuMapper;
import com.xiaosi.wx.mapper.SysUserMapper;
import com.xiaosi.wx.model.SysUser;
import com.xiaosi.wx.service.StuService;
import com.xiaosi.wx.vo.StuVo;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

    private final StuService stuService;
    private final RsaKeyProperties rsaKeyProperties;
    private final SysUserMapper sysUserMapper;

//    @PreAuthorize("hasPermission('USER1','READ')")
    @PostMapping("/add")
    public int getList(@RequestBody SysUser sysUser) throws IOException {
//        System.out.println(rsaKeyProperties.getPrivateRsaKey());
        Resource publicKey = rsaKeyProperties.getPublicKey();
//        System.out.println(IOUtils.toString(publicKey.getInputStream(), StandardCharsets.UTF_8));
//        System.out.println(rsaKeyProperties.getPublicRsaKey());
        return sysUserMapper.insert(sysUser);
    }
}
