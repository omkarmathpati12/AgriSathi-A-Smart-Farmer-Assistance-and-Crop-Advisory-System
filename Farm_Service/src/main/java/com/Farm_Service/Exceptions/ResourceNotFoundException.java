package com.Farm_Service.Exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message, Object value) {
        super(message.replace("{}",String.valueOf(value)));
    }
}
