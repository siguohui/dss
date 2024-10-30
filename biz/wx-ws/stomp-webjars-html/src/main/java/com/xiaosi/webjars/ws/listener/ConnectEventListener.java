package com.xiaosi.webjars.ws.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

/**
 * 监听器演示
 * 建立连接事件
 *
 * StompHeaderAccessor  简单消息传递协议中处理消息头的基类，
 * 通过这个类，可以获取消息类型(例如:发布订阅，建立连接断开连接)，会话id等
 */
@Component
public class ConnectEventListener implements ApplicationListener<SessionConnectEvent>{

    public void onApplicationEvent(SessionConnectEvent event) {
        System.out.println(event);
        StompHeaderAccessor headerAccessor =  StompHeaderAccessor.wrap(event.getMessage());
        System.out.println("【ConnectEventListener监听器事件 类型】"+headerAccessor.getCommand().getMessageType());
    }
}
