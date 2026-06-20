package com.deliveryapp.notificationservice.event;

import java.util.UUID;

public record OrderAssignedEvent(
        UUID orderId,
        UUID courierId
) { }
