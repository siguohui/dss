package com.xiaosi.wx.handler;

import com.xiaosi.wx.common.Constant;
import com.xiaosi.wx.pojo.JsonResult;
import com.xiaosi.wx.utils.RedisUtil;
import com.xiaosi.wx.utils.ResponseUtils;
import com.xiaosi.wx.utils.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BzSuccessHandler implements AuthenticationSuccessHandler {

    private final RedisUtil redisUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("登录成功");
        Object token = redisUtil.get(Constant.TOKEN_KEY_PREFIX + SecurityUtils.getUername(authentication));
        ResponseUtils.responseJson(response, JsonResult.success("登录成功",token));
    }
}
