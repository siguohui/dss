package com.xiaosi.wx.handler;

import com.xiaosi.wx.pojo.JsonResult;
import com.xiaosi.wx.utils.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BzFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)  {
        log.warn("登录失败"+e.getMessage());
        JsonResult res = JsonResult.fail("登录失败");
        if (e instanceof BadCredentialsException) {
            res.setMsg("用户名或者密码输入错误，登录失败");
        } else if (e instanceof DisabledException) {
            res.setMsg("账户被禁用，登录失败");
        } else if (e instanceof CredentialsExpiredException) {
            res.setMsg("密码过期，登录失败");
        } else if (e instanceof AccountExpiredException) {
            res.setMsg("账户过期，登录失败");
        } else if (e instanceof LockedException) {
            res.setMsg("账户被锁定，登录失败");
        }
        ResponseUtils.responseJson(response, res);
    }
}
