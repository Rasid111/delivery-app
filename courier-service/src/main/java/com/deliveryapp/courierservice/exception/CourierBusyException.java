package com.deliveryapp.courierservice.exception;

import java.util.UUID;

public class CourierBusyException extends RuntimeException {
    public CourierBusyException(UUID id) {
        super("Cannot delete a busy courier: " + id);
    }
}
