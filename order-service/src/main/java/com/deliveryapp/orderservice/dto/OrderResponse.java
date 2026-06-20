package com.deliveryapp.orderservice.dto;

import com.deliveryapp.orderservice.domain.OrderStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        UUID courierId,
        String pickupAddress,
        String deliveryAddress,
        BigDecimal price,
        OrderStatus status,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) { }
