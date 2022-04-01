package com.example.freshonline.event;


import com.example.freshonline.dto.CreateOrderDetail;
import org.springframework.context.ApplicationEvent;

public class CreateOrderEvent extends ApplicationEvent {

    private CreateOrderDetail detail;

    public CreateOrderEvent(Object source, CreateOrderDetail detail) {
        super(source);
        this.detail = detail;
    }

    public CreateOrderDetail getDetail() {
        return detail;
    }
}
