package com.deliveryapp.paymentservice.dto;

import com.deliveryapp.paymentservice.domain.PaymentStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record PaymentResponse(
        UUID id,
        UUID orderId,
        UUID courierId,
        BigDecimal deliveryPrice,
        BigDecimal earning,
        PaymentStatus status,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
