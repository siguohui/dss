package com.xiaosi.wx.eventObject;

import com.xiaosi.wx.eventObject.demo.RegNotificationListener;
import com.xiaosi.wx.eventObject.demo.SaveUserListener;
import com.xiaosi.wx.eventObject.demo.User;
import org.testng.annotations.Test;

public class App {

    public static void main(String[] args) {
        Source source = new Source();
        source.setName("av");

        source.registerEventListener((event)->{
            System.out.println("one....");
            System.out.println(event.getSource().getName());
            System.out.println(Thread.currentThread().getName());
        });

        source.registerEventListener((event)->{
            System.out.println("two....");
            System.out.println(event.getSource().getName());
            System.out.println(Thread.currentThread().getName());
        });

        source.handle();
    }
}
