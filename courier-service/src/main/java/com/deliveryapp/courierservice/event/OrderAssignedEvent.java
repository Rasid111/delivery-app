package com.deliveryapp.courierservice.event;

import java.util.UUID;

public record OrderAssignedEvent(
        UUID orderId,
        UUID courierId
) { }
