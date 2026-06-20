package com.deliveryapp.notificationservice.event;

import java.util.UUID;

public record OrderDeliveredEvent(
        UUID orderId,
        UUID courierId
) { }
