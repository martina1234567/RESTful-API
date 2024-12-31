package com.example.car_managment.exception;

public class GarageFullException extends RuntimeException {
    public GarageFullException(String message) {
        super(message);
    }
}
