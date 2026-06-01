package com.deliveryapp.paymentservice.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record CourierBalanceResponse(
        UUID courierId,
        BigDecimal balance,
        BigDecimal turnover,
        OffsetDateTime updatedAt
) {}
