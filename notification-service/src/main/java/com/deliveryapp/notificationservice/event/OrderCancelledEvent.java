package com.deliveryapp.notificationservice.event;

import java.util.UUID;

public record OrderCancelledEvent(
        UUID orderId,
        UUID courierId
) { }
