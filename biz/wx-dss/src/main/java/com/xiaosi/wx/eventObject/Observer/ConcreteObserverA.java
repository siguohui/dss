package com.xiaosi.wx.eventObject.Observer;


/**
 * 观察者对象
 * @author wangbo
 */
public class ConcreteObserverA implements Observer {

    @Override
    public void doNotify() {
        System.out.println("A被通知");
    }

}
