package com.deliveryapp.courierservice.event;

import java.util.UUID;

public record OrderDeliveredEvent(
        UUID orderId,
        UUID courierId
) { }
