package com.example.freshonline.schedule;


import com.example.freshonline.event.CustomEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


public class DeliveryOrderTask implements Runnable{

    @Autowired
    private CustomEventPublisher publisher;

    private Integer orderId;

    public DeliveryOrderTask(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public void run() {
        if (orderId == null){
            return;
        }

    }
}
