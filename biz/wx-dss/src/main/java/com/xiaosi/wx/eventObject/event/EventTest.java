package com.xiaosi.wx.eventObject.event;

public class EventTest {

    public static void main(String[] args) {
        EventDemo eventDemo = new EventDemo(new Student().setName("张三"));
        EventDemoListener eventDemoListener = new EventDemoListener();
        eventDemoListener.handler(eventDemo);
    }
}
