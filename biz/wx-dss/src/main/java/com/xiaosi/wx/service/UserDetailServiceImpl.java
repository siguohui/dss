package com.xiaosi.wx.service;

import com.xiaosi.wx.mapper.SysRoleMapper;
import com.xiaosi.wx.mapper.SysUserMapper;
import com.xiaosi.wx.model.SysRole;
import com.xiaosi.wx.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class UserDetailServiceImpl  implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名获取用户
        SysUser user = sysUserMapper.selectByUserName(username);
        if(user == null){
            throw new UsernameNotFoundException("User " + username + " was not found in db");
        }
        /*List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("role");*/
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        List<SysRole> sysRoleList = sysRoleMapper.selectByUserId(user.getId());
        for (SysRole sysRole : sysRoleList) {
            authorities.add(new SimpleGrantedAuthority(sysRole.getName()));
            //authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
//        UserDetails userDetails = User.withUsername(user.getName()).password(user.getPassword()).authorities(authorities).build();
//        return userDetails;
        return new User(user.getUsername(), user.getPassword(), authorities);
//        return new User(username,"123456", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }
}
