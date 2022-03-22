package com.example.freshonline.listener;


import com.example.freshonline.event.CreateOrderEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CustomEventListener {

    @EventListener
    public void handleCreateOrderEvent(CreateOrderEvent event) {

        // create order

        // record saled goods

        // delete goods from cart
    }
}
