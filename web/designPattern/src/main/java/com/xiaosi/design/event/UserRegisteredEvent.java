package com.xiaosi.design.event;

import org.springframework.context.ApplicationEvent;

public class UserRegisteredEvent extends ApplicationEvent {
    // 这里的User是一个包含用户相关信息的实体类
    private User user;

    // source：事件的源对象，用于表明这个事件是由哪个对象触发的
    // 具体作用下面订阅事件中解释
    public UserRegisteredEvent(Object source, User user) {
        super(source);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
