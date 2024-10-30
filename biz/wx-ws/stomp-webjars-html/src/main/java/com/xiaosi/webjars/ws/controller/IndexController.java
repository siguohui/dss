package com.xiaosi.webjars.ws.controller;

import com.xiaosi.webjars.ws.entity.Chat;
import com.xiaosi.webjars.ws.utils.RedisUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Date;

@RestController
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "success";
    }

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private RedisUtil redisUtil;

    @MessageMapping("/chat1")
    public void chat(Principal principal, Chat chat) {
        String from = principal.getName();
        chat.setFrom(from);
        System.out.println("11111111111111111111111111111111111111111");
        simpMessagingTemplate.convertAndSendToUser(chat.getTo(), "/queue/chat", chat);

    }

    @GetMapping("/rabbit")
    public void rabbit() {
        simpMessagingTemplate.convertAndSend("/topic/scdc.alarm", "测试");
    }
    @GetMapping("/msg")
    public String message() {
        rabbitTemplate.convertAndSend("addFavorite.direct","addFavorite.success",1);

        String context = "hello " + new Date();
        System.out.println("Sender : " + context);
        amqpTemplate.convertAndSend("hello", context);
        return "success";
    }

    @GetMapping("/send")
    public String send() {
        redisUtil.sendMsg("chat","测试");
        return "success";
    }

    @PostMapping("/test")
    public String test(@RequestBody @Validated Chat chat) {
        redisUtil.sendMsg("chat","测试");
        return "success";
    }

}
