package com.xiaosi.wx.eventObject.demo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
public class User {

    private String userId;

    private List<EventListener> EventListenerList = new ArrayList<>();

    public User(String userId){
        this.userId = userId;
    }


    public void saveUser()
    {
        log.info("save user");
    }


    public void setListener(EventListener listener)
    {
        EventListenerList.add(listener);
    }

    public void register() {
        for (EventListener listener : EventListenerList)
        {
            UserEvent event = new UserEvent(this);
            listener.handleRegSuccess(event);
        }
    }
}
