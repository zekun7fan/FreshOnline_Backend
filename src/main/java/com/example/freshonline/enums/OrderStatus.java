package com.example.freshonline.enums;

public enum OrderStatus {

    CREATE(0),
    CANCEL(1),
    PAID(2),
    IN_DELIVERY(3),
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
