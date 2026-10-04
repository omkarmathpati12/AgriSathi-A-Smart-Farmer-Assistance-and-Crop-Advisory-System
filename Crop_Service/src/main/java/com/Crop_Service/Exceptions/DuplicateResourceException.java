package com.Crop_Service.Exceptions;

public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String resourceName, Object value) {
        super(resourceName + " already exists with value: " + value);
    }
}
