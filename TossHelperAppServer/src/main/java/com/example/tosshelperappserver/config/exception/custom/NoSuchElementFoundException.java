package com.example.tosshelperappserver.config.exception.custom;

public class NoSuchElementFoundException extends RuntimeException {
    public NoSuchElementFoundException(String message) {
        super(message);
    }
}
