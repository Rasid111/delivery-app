package com.deliveryapp.orderservice.exception;

public class NoAvailableCourierException extends RuntimeException {
    public NoAvailableCourierException() {
        super("No available courier");
    }
}
