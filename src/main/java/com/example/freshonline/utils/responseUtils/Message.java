package com.example.freshonline.utils.responseUtils;

public enum Message {

    ADD_SUCCESS("add successful"),
    MOD_SUCCESS("modify successful"),
    ADD_FAIL("add failed"),
    ;


    private String msg;

    Message(String msg){
        this.msg = msg;
    }
}
