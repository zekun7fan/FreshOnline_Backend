package com.example.freshonline.exception;

public enum SearchErrorCode implements ICustomizeErrorCode{

    SEARCH_PARAM_FORMAT_EXCEPTION(200, "search paramater number format exception"),
    ;

    private Integer code;
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    SearchErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
