package com.deliveryapp.notificationservice.event;

import java.util.UUID;

public record OrderCreatedEvent(
        UUID orderId,
        UUID courierId
) { }
