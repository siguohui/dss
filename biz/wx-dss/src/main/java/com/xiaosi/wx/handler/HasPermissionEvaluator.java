package com.xiaosi.wx.handler;

import org.springframework.context.annotation.Bean;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.io.Serializable;
import java.util.Collection;

/**
 * @description: 自定义权限评估器
 * @author: mml
 * @create: 2024/02/17
 */
@Component
public class HasPermissionEvaluator implements PermissionEvaluator {

    /*@Bean
    PermissionEvaluator resourcePermissionEvaluator() {
        return new ResourcePermissionEvaluator((targetDomainObject, permission) -> {
            //TODO 这里形式其实可以不固定
            String key = targetDomainObject + ":" + permission;
            //TODO  查询 key 和  authority 的关联关系
            //  模拟 permission 关联角色   根据key 去查 grantedAuthorities
            Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            return "USER:READ".equals(key) ? grantedAuthorities : new HashSet<>();
        });
    }*/

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        // 获取当前用户的角色
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            // 权限判断
            if (antPathMatcher.match(authority.getAuthority(), (String) permission)){
                // 说明有权限
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
