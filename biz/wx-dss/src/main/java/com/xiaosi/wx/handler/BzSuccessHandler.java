package com.xiaosi.wx.handler;

import com.xiaosi.wx.pojo.JsonResult;
import com.xiaosi.wx.token.JwtTokenProvider;
import com.xiaosi.wx.utils.ResponseUtils;
import com.xiaosi.wx.utils.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.groovy.util.Maps;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BzSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenUtils tokenUtils;
    private final JwtTokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("登录成功");
        String token = tokenUtils.getRedisToken();
        System.out.println(tokenProvider.createJwtToken(authentication));
        ResponseUtils.responseJson(response, JsonResult.success("登录成功", Maps.of(  "tokenType","Bearer", "token", token, "access-token", "Bearer " + token)));
    }
}
