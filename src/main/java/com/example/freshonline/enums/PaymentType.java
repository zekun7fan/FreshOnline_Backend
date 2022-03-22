package com.example.freshonline.enums;

public enum PaymentType {

    CREDIT(0),
    ;

    private int type;

    public int getType() {
        return type;
    }

    PaymentType(int type){
        this.type = type;
    }
}
