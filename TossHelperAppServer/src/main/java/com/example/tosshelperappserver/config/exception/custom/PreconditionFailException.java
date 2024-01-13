package com.example.tosshelperappserver.config.exception.custom;

public class PreconditionFailException extends RuntimeException {
    public PreconditionFailException(String message) {
        super(message);
    }
}
