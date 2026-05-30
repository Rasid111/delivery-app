package com.deliveryapp.orderservice.event;

import java.time.OffsetDateTime;
import java.util.UUID;

public record OrderAssignedEvent(
        UUID orderId,
        UUID courierId,
        OffsetDateTime assignedAt
) { }