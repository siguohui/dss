package com.xiaosi.wx.eventObject.event;

import java.util.EventListener;

public class EventDemoListener implements EventListener {

    public void handler(EventDemo eventDemo) {
        System.out.println(eventDemo.getSource().toString());
    }
}
