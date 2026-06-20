package com.deliveryapp.paymentservice.event;

import java.util.UUID;

public record OrderCancelledEvent(
        UUID orderId,
        UUID courierId
) { }
