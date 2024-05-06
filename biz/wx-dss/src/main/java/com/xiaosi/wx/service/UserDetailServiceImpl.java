package com.xiaosi.wx.service;

import com.xiaosi.wx.exception.CustomException;
import com.xiaosi.wx.mapper.SysMenuMapper;
import com.xiaosi.wx.mapper.SysUserMapper;
import com.xiaosi.wx.model.SysMenu;
import com.xiaosi.wx.model.SysRole;
import com.xiaosi.wx.model.SysUser;
import jakarta.annotation.Resource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Component
public class UserDetailServiceImpl  implements UserDetailsService {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysMenuMapper sysMenuMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名获取用户
        SysUser sysUser = sysUserMapper.selectByUserName(username);
        if(sysUser == null){
            throw new UsernameNotFoundException("User " + username + " was not found in db");
        }
        if (CollectionUtils.isEmpty(sysUser.getRoleSet())) {
            throw new CustomException("账号未分配角色!");
        }

        if(Objects.isNull(sysUser.getPerms())){
            sysUser.setPerms(new HashSet<>());
        }

        // 权限查询
        Set<SysMenu> menus = sysMenuMapper.selectMenuByRoleId(sysUser.getRoleIds());
        sysUser.setMenuSet(menus);

        return sysUser;
//        return new User(username,"123456", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }
}
