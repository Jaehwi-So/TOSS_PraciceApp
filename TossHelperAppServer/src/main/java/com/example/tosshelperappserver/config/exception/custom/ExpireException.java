package com.example.tosshelperappserver.config.exception.custom;

public class ExpireException extends RuntimeException{
    public ExpireException(String message) {
        super(message);
    }
}
