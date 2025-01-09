package com.xiaosi.design.controller;

import com.xiaosi.design.event.User;
import com.xiaosi.design.event.UserRegistrationService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Setter(onMethod_ = @Autowired)
    private UserRegistrationService userRegistrationService;

    @GetMapping("/index")
    public String index(){
        userRegistrationService.registerUser(User.builder().name("张三").email("8857807@163.com").build());
        return "success";
    }
}
