package com.xiaosi.wx.eventObject;

import cn.hutool.core.thread.ThreadUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public class Source {

    private String name;

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private Set<EventListener> listenerSet = new HashSet<>();

    public void registerEventListener(EventListener eventListener){
        if (eventListener != null){
            listenerSet.add(eventListener);
        }
    }

    public void handle(){
        for (EventListener eventListener : listenerSet) {
            ExecutorService executor = ThreadUtil.newExecutor();
            executor.execute(()-> {
                Event event = new Event(this);
                eventListener.callback(event);
            });
            executor.shutdown();
        }
    }

}
