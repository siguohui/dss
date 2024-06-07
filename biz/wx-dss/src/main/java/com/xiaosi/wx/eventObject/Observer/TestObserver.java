package com.xiaosi.wx.eventObject.Observer;

/**
 * 观察者模式测试类
 * @author wangbo
 */
public class TestObserver {

    /**
     * @param args
     */
    public static void main(String[] args) {
        //实例化被观察者对象
        Subject subject = new ConcreteSubject();

        //实例化观察者对象
        Observer observerA = new ConcreteObserverA();
        Observer observerB = new ConcreteObserverB();

        //注册观察者对象
        subject.registerObserver(observerA);
        subject.registerObserver(observerB);

        //被观察者通知观察者执行
        subject.notifyObservers();

        System.out.println("------------------------------");
        //删除观察者对象
        subject.removeObserver(observerA);

        subject.notifyObservers();
    }

}
