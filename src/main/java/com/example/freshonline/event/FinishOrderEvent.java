package com.example.freshonline.event;

import org.springframework.context.ApplicationEvent;

public class FinishOrderEvent extends ApplicationEvent {

    private Integer orderId;

    private long delay;


    public FinishOrderEvent(Object source, Integer orderId, long delay) {
        super(source);
        this.orderId = orderId;
        this.delay = delay;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public long getDelay() {
        return delay;
    }
}
