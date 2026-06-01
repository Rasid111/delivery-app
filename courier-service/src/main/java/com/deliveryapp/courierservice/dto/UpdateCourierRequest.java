package com.deliveryapp.courierservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCourierRequest(
        @NotBlank
        @Size(max = 255)
        String name,
        @NotBlank
        @Size(max = 50)
        String phone
) {}
