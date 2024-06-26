package com.xiaosi.wx.handler;

import com.xiaosi.wx.exception.CustomException;
import com.xiaosi.wx.pojo.JsonResult;
import com.xiaosi.wx.utils.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class BzEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.warn("未认证{}",authException.getMessage());
        JsonResult res = JsonResult.fail("请登录");
        if (authException instanceof CustomException) {
            res.setMsg(authException.getMessage());
        }
        ResponseUtils.responseJson(response, res);
    }
}
