package com.xiaosi.wx.eventObject.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.EventObject;

public class EventDemo extends EventObject {

    public EventDemo(Object source) {
        super(source);
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
