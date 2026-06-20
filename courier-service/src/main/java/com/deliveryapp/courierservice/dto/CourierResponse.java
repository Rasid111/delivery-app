package com.deliveryapp.courierservice.dto;

import com.deliveryapp.courierservice.domain.CourierStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CourierResponse(
        UUID id,
        String name,
        String phone,
        CourierStatus status,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
