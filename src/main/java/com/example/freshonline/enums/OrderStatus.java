package com.example.freshonline.enums;

public enum OrderStatus {

    CREATED(0),
    CANCELLED(1),
    PAID(2),
    IN_DELIVERING(3),
    FINISHED(4)
    ;



    private int status;

    public int getStatus() {
        return status;
    }

    OrderStatus(int status){
        this.status = status;
    }



}
