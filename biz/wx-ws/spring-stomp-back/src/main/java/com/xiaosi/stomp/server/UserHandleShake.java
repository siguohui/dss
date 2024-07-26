package com.xiaosi.stomp.server;

import com.xiaosi.stomp.model.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

/**
 * @ClassName: UserHandleShake
 * @Description:
 * @Author: 88578
 * @Date: 2022/5/18 14:46
 */
public class UserHandleShake extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        System.out.println("shake");
        UserInfo info = new UserInfo();
        info.setUserName(servletRequest.getParameter("userName"));
        return info;
    }
}

