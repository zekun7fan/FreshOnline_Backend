package com.example.freshonline.exception;



public enum CustomizeErrorCode implements ICustomizeErrorCode {

    EXAMPLE_EXCEPTION(200, "example exception"),
    ;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
