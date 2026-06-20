package com.deliveryapp.orderservice.exception;

import com.deliveryapp.orderservice.domain.OrderStatus;

public class IllegalOrderTransitionException extends RuntimeException {
    public IllegalOrderTransitionException(OrderStatus from, OrderStatus to) {
        super("Cannot transition order from " + from + " to " + to);
    }
}
