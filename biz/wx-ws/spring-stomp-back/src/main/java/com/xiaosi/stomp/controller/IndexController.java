package com.xiaosi.stomp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired
    private SimpMessagingTemplate template;

    @GetMapping("/")
    public String index() {
        template.convertAndSend("/user/60/message", "内容提取失败，我是默认消息");
//        template.convertAndSendToUser("1", "/message", "内容提取失败，我是默认消息");
        return "success";
    }
}
