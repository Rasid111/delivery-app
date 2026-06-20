package com.deliveryapp.orderservice.event;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record OrderDeliveredEvent(
        UUID orderId,
        UUID courierId,
        BigDecimal price,
        OffsetDateTime deliveredAt
) { }