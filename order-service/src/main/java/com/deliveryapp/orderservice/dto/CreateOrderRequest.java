package com.deliveryapp.orderservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateOrderRequest(
        @NotBlank
        @Size(max = 500)
        String pickupAddress,
        @NotBlank
        @Size(max = 500)
        String deliveryAddress
) {}
