package com.deliveryapp.courierservice.event;

import java.util.UUID;

public record OrderCancelledEvent(
        UUID orderId,
        UUID courierId
) { }
