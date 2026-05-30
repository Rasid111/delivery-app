package com.deliveryapp.orderservice.client;

import com.deliveryapp.orderservice.client.dto.AvailableCourierResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Lets order-service run without courier-service: activate with the {@code stub-courier} profile.
 */
@Component
@Primary
@Profile("stub-courier")
public class StubCourierClient implements CourierClient {

    @Override
    public AvailableCourierResponse getAvailable() {
        return new AvailableCourierResponse(UUID.randomUUID(), "Stub Courier");
    }
}
