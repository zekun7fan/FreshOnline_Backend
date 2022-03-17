package com.example.freshonline.exception;

public class RedisException extends RuntimeException{
    private String message;
    public RedisException(String string) {
        this.message = string;
    }
    public String getMessage() {
        return message;
    }
}
