package com.xiaosi.wx.eventObject.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.EventObject;

@Slf4j
public class UserEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public UserEvent(Object source) {
        super(source);
    }

    public void processNotification(String id){
        log.info(id + ":邮件发送");
    }
}
