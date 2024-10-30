package com.xiaosi.webjars.ws.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    /**
     * 前往认证(登录)页面
     * @return
     */
    @RequestMapping("/login.html")
    public String toLogin(){
        return "login";
    }

    @GetMapping("/chat")
    public String chat(){
        return "chat";
    }

    @GetMapping("/wr")
    public String rabbit(){
        return "rabbit";
    }
}
