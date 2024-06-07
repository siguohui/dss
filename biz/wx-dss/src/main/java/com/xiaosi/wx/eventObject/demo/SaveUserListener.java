package com.xiaosi.wx.eventObject.demo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SaveUserListener implements EventListener {
    @Override
    public void handleRegSuccess(UserEvent event) {
        User user = (User) event.getSource();
        user.saveUser();
        log.info("保存用户");
    }
}
