package com.xiaosi.design.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class WelcomeEmailSender implements ApplicationListener<UserRegisteredEvent> {
    @Override
    public void onApplicationEvent(UserRegisteredEvent event) {
        User user = event.getUser();
        // 发送欢迎邮件给用户的逻辑，如调用邮件发送接口等
        System.out.println("Sending welcome email to " + user.getEmail());
    }
}
