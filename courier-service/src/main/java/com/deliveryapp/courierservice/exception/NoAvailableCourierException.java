package com.deliveryapp.courierservice.exception;

public class NoAvailableCourierException extends RuntimeException {
    public NoAvailableCourierException() {
        super("No available courier");
    }
}
