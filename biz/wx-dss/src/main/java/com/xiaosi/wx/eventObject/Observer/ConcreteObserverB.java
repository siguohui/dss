package com.xiaosi.wx.eventObject.Observer;


/**
 * 观察者对象
 * @author wangbo
 */
public class ConcreteObserverB implements Observer {

    @Override
    public void doNotify() {
        System.out.println("B被通知");
    }

}
