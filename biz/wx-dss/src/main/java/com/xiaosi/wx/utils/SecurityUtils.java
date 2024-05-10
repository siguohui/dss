package com.xiaosi.wx.utils;

import com.xiaosi.wx.entity.SysUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;
import java.util.Optional;

public class SecurityUtils {

    public static SecurityContext getContext(){
        return SecurityContextHolder.getContext();
    }

    public static Authentication getAuthentication() {
        return Optional.ofNullable(getContext()).map(m->m.getAuthentication()).orElse(null);
    }

    public static Object getPrincipal() {
        return Optional.ofNullable(getAuthentication()).map(m->m.getPrincipal()).orElse(null);
    }

    public static SysUser getSysUser(){
        return getPrincipal() instanceof SysUser ? (SysUser) getPrincipal() : null;
    }
    public static String getUername(){
        Object principal = getPrincipal();
        if(Objects.isNull(principal)){
            return "";
        }
        if(principal instanceof UserDetails){
           return  ((UserDetails) principal).getUsername();
        }
        return principal.toString();
    }

    public static String getUername(Authentication authentication){
        Object principal = authentication.getPrincipal();
        if(principal instanceof UserDetails){
            return  ((UserDetails) principal).getUsername();
        }
        return principal.toString();
    }
}
