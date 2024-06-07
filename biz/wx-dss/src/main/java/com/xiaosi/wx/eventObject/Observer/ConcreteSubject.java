package com.xiaosi.wx.eventObject.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 被观察者对象
 * @author wangbo
 */
public class ConcreteSubject implements Subject {

    //观察者容器
    private List<Observer> list = new ArrayList();

    @Override
    public void notifyObservers() {
        for (Observer observer : list) {
            observer.doNotify();
        }
    }

    @Override
    public void registerObserver(Observer observer) {
        list.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        list.remove(observer);
    }

}
