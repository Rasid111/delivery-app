package com.deliveryapp.paymentservice.exception;

import java.util.UUID;

public class CourierBalanceNotFoundException extends RuntimeException {
    public CourierBalanceNotFoundException(UUID courierId) {
        super("Courier balance not found: " + courierId);
    }
}
