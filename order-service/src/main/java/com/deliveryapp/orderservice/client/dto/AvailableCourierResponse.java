package com.deliveryapp.orderservice.client.dto;

import java.util.UUID;

public record AvailableCourierResponse(
        UUID id,
        String name
) { }
