package org.xiaosi.stomp.listener;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.xiaosi.stomp.entity.ChatMessage;

/**
 * 监听器演示
 * 建立连接事件
 *
 * SessionSubscribeEvent   订阅事件
 * SessionUnsubscribeEvent 取消订阅事件
 * SessionDisconnectEvent  断开连接事件
 * SessionDisconnectEvent  建立连接事件
 *
 * StompHeaderAccessor  简单消息传递协议中处理消息头的基类，
 * 通过这个类，可以获取消息类型(例如:发布订阅，建立连接断开连接)，会话id等
 */
@Slf4j
@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    /*@Component
    public class ConnectEventListener implements ApplicationListener<SessionConnectEvent> {
        public void onApplicationEvent(SessionConnectEvent event) {
            System.out.println(event);
            StompHeaderAccessor headerAccessor =  StompHeaderAccessor.wrap(event.getMessage());
            System.out.println("ws的sessionId--------------------"+headerAccessor.getSessionId());;
            System.out.println("【ConnectEventListener监听器事件 类型】"+headerAccessor.getCommand().getMessageType());
        }
    }*/

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
//        log.info("{}", event);
//        StompHeaderAccessor headerAccessor =  StompHeaderAccessor.wrap(event.getMessage());
//        log.info("ws的sessionId--------------------{}", headerAccessor.getSessionId());;
//        log.info("【ConnectEventListener监听器事件 类型】{}", headerAccessor.getCommand().getMessageType());
    }

    @EventListener
    private void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
//        log.info("Subscribe event : {}", JSON.toJSONString(event));
//        MessageHeaders headers = event.getMessage().getHeaders();
//        System.out.println(headers+"---------------------------------------------->>>>>>>>>>>>--------------");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null) {
            logger.info("User Disconnected : " + username);

            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType(ChatMessage.MessageType.LEAVE);
            chatMessage.setSender(username);

            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
