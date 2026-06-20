package com.deliveryapp.orderservice.exception;

import com.deliveryapp.orderservice.domain.OrderStatus;

public class OrderNotCancellableException extends RuntimeException {
    public OrderNotCancellableException(OrderStatus status) {
        super("Cannot cancel order in status " + status);
    }
}
