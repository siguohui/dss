package com.xiaosi.wx.controller;

import com.xiaosi.wx.config.RsaKeyProperties;
import com.xiaosi.wx.mapper.SysUserMapper;
import com.xiaosi.wx.entity.SysUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
    private final SysUserMapper sysUserMapper;

    @PreAuthorize("hasPermission('USER1','READ')")
    @PostMapping("/add")
    public int getList(@RequestBody SysUser sysUser) throws IOException {
//        System.out.println(rsaKeyProperties.getPrivateRsaKey());
        Resource publicKey = rsaKeyProperties.getPublicKey();
//        System.out.println(IOUtils.toString(publicKey.getInputStream(), StandardCharsets.UTF_8));
//        System.out.println(rsaKeyProperties.getPublicRsaKey());
        return sysUserMapper.insert(sysUser);
    }
}
