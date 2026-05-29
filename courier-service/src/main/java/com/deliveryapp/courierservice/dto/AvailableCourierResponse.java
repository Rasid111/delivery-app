package com.deliveryapp.courierservice.dto;

import java.util.UUID;

public record AvailableCourierResponse(
        UUID id,
        String name
) {}
