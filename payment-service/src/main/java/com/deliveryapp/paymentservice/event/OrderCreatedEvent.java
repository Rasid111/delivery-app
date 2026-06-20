package com.deliveryapp.paymentservice.event;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record OrderCreatedEvent(
        UUID orderId,
        UUID courierId,
        BigDecimal price,
        String pickupAddress,
        String deliveryAddress,
        OffsetDateTime createdAt
) { }
