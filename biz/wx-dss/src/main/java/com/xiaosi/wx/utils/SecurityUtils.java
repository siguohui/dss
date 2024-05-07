package com.xiaosi.wx.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {

    public static SecurityContext getContext(){
        return SecurityContextHolder.getContext();
    }

    public static Authentication getAuthentication() {
        return getContext().getAuthentication();
    }

    public static Object getPrincipal() {
        return getAuthentication().getPrincipal();
    }
    public static String getUername(){
        Object principal = getPrincipal();
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
