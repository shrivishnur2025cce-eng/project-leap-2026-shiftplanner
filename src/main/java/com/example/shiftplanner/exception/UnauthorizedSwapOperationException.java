package com.example.shiftplanner.exception;

public class UnauthorizedSwapOperationException extends RuntimeException {
    public UnauthorizedSwapOperationException(String message) {
        super(message);
    }
}
