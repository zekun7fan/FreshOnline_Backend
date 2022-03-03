package com.example.freshonline.exception;



public enum CustomizeErrorCode implements ICustomizeErrorCode {

    EXAMPLE_EXCEPTION(200, "example exception"),
    USER_INFO_INCORRECT(200, "email or password incorrect"),
    EMAIL_ALREADY_REGISTERED(200, "this email has already be registered"),
    SEARCH_PARAM_FORMAT_EXCEPTION(200, "search parameter number format exception"),
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
