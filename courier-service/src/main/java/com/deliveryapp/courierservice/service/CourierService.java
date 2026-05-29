package com.deliveryapp.courierservice.service;

import com.deliveryapp.courierservice.domain.Courier;
import com.deliveryapp.courierservice.domain.CourierStatus;
import com.deliveryapp.courierservice.dto.CreateCourierRequest;
import com.deliveryapp.courierservice.exception.CourierNotFoundException;
import com.deliveryapp.courierservice.exception.NoAvailableCourierException;
import com.deliveryapp.courierservice.repository.CourierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class CourierService {
    private final CourierRepository courierRepository;

    public Courier create(CreateCourierRequest request) {
        Courier courier = new Courier();
        courier.setName(request.name());
        courier.setPhone(request.phone());
        courier.setStatus(CourierStatus.FREE);
        return courierRepository.save(courier);
    }

    @Transactional(readOnly = true)
    public Courier getById(UUID id) {
        return courierRepository.findById(id)
                .orElseThrow(() -> new CourierNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Courier getAvailable() {
        return courierRepository.findFirstByStatus(CourierStatus.FREE)
                .orElseThrow(NoAvailableCourierException::new);
    }
}
