package com.deliveryapp.orderservice.event;

import java.util.UUID;

public record OrderCancelledEvent(
        UUID orderId,
        UUID courierId
) { }
