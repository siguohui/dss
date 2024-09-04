package com.xiaosi.stomp.controller;

import com.xiaosi.stomp.model.UserInfo;
import com.xiaosi.stomp.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
    @SendTo("/topic/msg")
    public String testWs(Map<String, Object> params) {

        UserInfo userInfo = new UserInfo();
        userInfo.setMessage((String) params.getOrDefault("content", "内容提取失败，我是默认消息"));
        template.convertAndSendToUser(String.valueOf(params.get("userName")), "/chat", params.getOrDefault("content", "内容提取失败，我是默认消息"));
        return JsonUtil.parseObjToJson(userInfo);
    }


    @GetMapping("/message")
    public String testWs1() {
        UserInfo userInfo = new UserInfo();
        userInfo.setMessage("内容提取失败，我是默认消息");
        template.convertAndSend("/topic/msg", userInfo);
        return JsonUtil.parseObjToJson(userInfo);
    }
}
