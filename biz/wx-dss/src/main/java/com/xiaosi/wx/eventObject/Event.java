package com.xiaosi.wx.eventObject;

public class Event {

    private final Source source;

    public Event(Source source) {
        if(source == null)
            throw new IllegalArgumentException("null source");
        this.source = source;
    }


    public Source getSource() {
        return source;
    }
}
