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

        CreateOrderEvent createOrderEvent = new CreateOrderEvent(detail, detail);
        publisher.publishEvent(createOrderEvent);
    }

    public void publishDeliveryOrderEvent(Integer orderId, long handleDelay){
        DeliveryOrderEvent deliveryOrderEvent = new DeliveryOrderEvent(new Object(), orderId, handleDelay);
        publisher.publishEvent(deliveryOrderEvent);

    }


    public void publishFinishOrderEvent(Integer orderId, long handleDelay){
        FinishOrderEvent finishOrderEvent = new FinishOrderEvent(new Object(), orderId, handleDelay);
        publisher.publishEvent(finishOrderEvent);
    }


}
