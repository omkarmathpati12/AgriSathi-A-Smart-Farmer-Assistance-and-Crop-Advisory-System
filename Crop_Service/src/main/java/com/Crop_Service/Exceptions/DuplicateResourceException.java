package com.Crop_Service.Exceptions;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message, Object value) {

        super(message.replace("{}",String.valueOf(value)));
    }
}
