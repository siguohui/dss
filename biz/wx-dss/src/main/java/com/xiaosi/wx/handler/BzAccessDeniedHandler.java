package com.xiaosi.wx.handler;

import com.xiaosi.wx.exception.CustomException;
import com.xiaosi.wx.pojo.JsonResult;
import com.xiaosi.wx.utils.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BzAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException AccessException) {
        log.warn("授权失败",AccessException.getMessage());
        JsonResult res = JsonResult.fail("无权授权访问");
       /* if (AccessException instanceof CustomException) {
            res.setMsg(AccessException.getMessage());
        }*/
        ResponseUtils.responseJson(response, res);
    }
}
