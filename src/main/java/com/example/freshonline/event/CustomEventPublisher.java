package com.example.freshonline.event;


import com.example.freshonline.dto.CreateOrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class CustomEventPublisher {

    @Autowired
    private ApplicationEventPublisher publisher;

    public void publishCreateOrderEvent(CreateOrderDetail detail) {

        CreateOrderEvent createOrderEvent = new CreateOrderEvent(null, detail);
        publisher.publishEvent(createOrderEvent);

    }


}
