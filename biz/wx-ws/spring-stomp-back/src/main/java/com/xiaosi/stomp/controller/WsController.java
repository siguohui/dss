package com.xiaosi.stomp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * @author dengsx
 * @create 2021/07/19
 **/
@MessageMapping("/chat")
@Controller
public class WsController {
    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/message")
    public void testWs(Map<String, Object> params) {
        template.convertAndSendToUser(String.valueOf(params.get("userName")), "/chat", params.getOrDefault("content", "内容提取失败，我是默认消息"));
    }
}
