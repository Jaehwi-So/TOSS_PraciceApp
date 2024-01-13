package com.example.tosshelperappserver.config.exception.custom;

public class AlreadyExistElementException extends RuntimeException{
    public AlreadyExistElementException(String message) {
        super(message);
    }
}
