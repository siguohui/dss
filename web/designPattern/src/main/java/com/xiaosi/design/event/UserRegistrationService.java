package com.xiaosi.design.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public void registerUser(User user) {
        // 执行用户注册逻辑，如保存用户信息到数据库等

        // 注册成功后发布事件
        UserRegisteredEvent event = new UserRegisteredEvent(this, user);
        eventPublisher.publishEvent(event);
    }
}
