package com.xiaosi.wx.eventObject.demo;

import org.testng.annotations.Test;

public class Mytest {

    @Test
    public void mytest(){
        User user = new User("123456");
        EventListener listener1 = new SaveUserListener();
        EventListener listener2 = new RegNotificationListener();
        user.setListener(listener1);
        user.setListener(listener2);
        user.register();
    }
}
