package com.xiaosi.design.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AnotherWelcomeEmailSender {
    @EventListener
    @Async
    public void handleUserRegisteredEvent(UserRegisteredEvent event) {
        User user = event.getUser();
        // 发送欢迎邮件的逻辑
        System.out.println("Another welcome email sent to " + user.getEmail());
    }

    @EventListener
    public void onUserRegistered(UserRegisteredEvent event) {
        Object source = event.getSource();
//        if (source instanceof WebRegistrationService) {
//            // 处理通过 Web 注册的用户
//        } else if (source instanceof ApiRegistrationService) {
//            // 处理通过 API 注册的用户
//        }
    }
}
