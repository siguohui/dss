package com.xiaosi.wx.handler;

import com.xiaosi.wx.pojo.JsonResult;
import com.xiaosi.wx.utils.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BzSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("登录成功");
        ResponseUtils.responseJson(response, JsonResult.success("登录成功",request.getAttribute("accessToken")));
    }
}
