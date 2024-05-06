package com.xiaosi.wx.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface PermService {

    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
