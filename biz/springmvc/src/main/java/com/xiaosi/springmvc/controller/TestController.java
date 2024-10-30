package com.xiaosi.springmvc.controller;

import com.xiaosi.springmvc.annotation.Token;
import com.xiaosi.springmvc.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/user/find")
    public User find(@Token User user){
        return user;
    }
}
