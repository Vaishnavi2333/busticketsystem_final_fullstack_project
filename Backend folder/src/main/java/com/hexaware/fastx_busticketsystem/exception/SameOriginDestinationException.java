package com.hexaware.fastx_busticketsystem.exception;

public class SameOriginDestinationException extends RuntimeException {
    public SameOriginDestinationException(String message) {
        super(message);
    }
}