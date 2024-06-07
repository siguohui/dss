package com.xiaosi.wx.eventObject.demo;


public class RegNotificationListener implements EventListener {
    @Override
    public void handleRegSuccess(UserEvent event) {
        User user = (User) event.getSource();
        event.processNotification(user.getUserId());
    }
}
