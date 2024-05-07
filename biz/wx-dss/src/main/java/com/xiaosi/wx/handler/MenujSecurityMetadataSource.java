package com.xiaosi.wx.handler;

import com.xiaosi.wx.mapper.SysMenuMapper;
import com.xiaosi.wx.entity.SysMenu;
import jakarta.annotation.Resource;
import org.apache.groovy.util.Maps;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import java.security.Permission;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Component
public class MenujSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Resource
    SysMenuMapper sysMenuMapper;
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    /*@Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) o).getFullRequestUrl();
        List<Menu> menus = menuService.getAllMenusWithRole();
        for (Menu menu: menus){
            if (antPathMatcher.match(menu.getUrl(), requestUrl)){
                List<Role> roles = menu.getRoles();
                String[] str = new String[roles.size()];
                for (int i=0; i<roles.size(); i++){
                    str[i] = roles.get(i).getName();
                }
                return SecurityConfig.createList(str);
            }
        }
        // 没有匹配上的，只要登录之后就可以访问，这里“ROLE_LOGIN”只是一个标记，有待进一步处理。
        return SecurityConfig.createList("ROLE_LOGIN");
    }*/

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        System.out.println(((FilterInvocation) object).getFullRequestUrl());
        String requestURI = ((FilterInvocation) object).getRequest().getRequestURI();

        if(antPathMatcher.match("/captcha/**", requestURI) || antPathMatcher.match("/favicon.ico", requestURI)){
            return SecurityConfig.createList("NO_AUTH");
        }

        List<SysMenu> sysMenus = sysMenuMapper.selectByMap(Maps.of("type", 2, "path", requestURI));

        if(sysMenus.isEmpty()){
            return SecurityConfig.createList("NO_AUTH");
        }

        for (SysMenu sysMenu : sysMenus) {
            if (antPathMatcher.match(sysMenu.getPath(), requestURI)) {
                return SecurityConfig.createList(sysMenu.getPerms());
            }
        }
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
