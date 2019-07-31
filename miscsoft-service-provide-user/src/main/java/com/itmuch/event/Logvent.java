package com.itmuch.event;

import org.springframework.context.ApplicationEvent;

public class Logvent extends ApplicationEvent {

    private String msg;

    public Logvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
