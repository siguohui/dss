package com.xiaosi.design.observe;

import java.util.ArrayList;
import java.util.List;

public class Weibo {
    private List<Observer> observers = new ArrayList<>();

    public void follow(Observer observer) {
        observers.add(observer);
    }

    public void post(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}
