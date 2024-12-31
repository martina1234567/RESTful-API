package com.example.car_managment.exception;

// Extending RuntimeException for unchecked exception
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message); // Calling the constructor of RuntimeException
    }
}
