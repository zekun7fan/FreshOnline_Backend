package com.example.freshonline.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEvent;


public class DeliveryOrderEvent extends ApplicationEvent {

    private Integer orderId;

    private long delay;

    public DeliveryOrderEvent(Object source, Integer orderId, long delay) {
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
