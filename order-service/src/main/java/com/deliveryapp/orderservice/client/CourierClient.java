package com.deliveryapp.orderservice.client;

import com.deliveryapp.orderservice.client.dto.AvailableCourierResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "courier-service", url = "${courier-service.url}")
public interface CourierClient {

    @GetMapping("/api/couriers/available")
    AvailableCourierResponse getAvailable();
}
