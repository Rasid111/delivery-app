package com.deliveryapp.orderservice.domain;

public enum OrderStatus {
    CREATED,
    ASSIGNED,
    PICKED_UP,
    DELIVERED;

    public boolean canTransitionTo(OrderStatus target) {
        return switch (this) {
            case CREATED -> target == ASSIGNED;
            case ASSIGNED -> target == PICKED_UP;
            case PICKED_UP -> target == DELIVERED;
            case DELIVERED -> false;
        };
    }

    public boolean isTerminal() {
        return this == DELIVERED;
    }
}
