package com.xiaosi.wx.eventObject.Observer;

/**
 * 被观察者对象接口
 * @author wangbo
 */
public interface Subject {

    /**
     * 注册观察者
     * @param observer
     */
    public void registerObserver(Observer observer);

    /**
     * 删除观察者
     * @param observer
     */
    public void removeObserver(Observer observer);

    /**
     * 通知观察者
     */
    public void notifyObservers();
}
