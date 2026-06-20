package com.deliveryapp.courierservice.service;

import com.deliveryapp.courierservice.domain.Courier;
import com.deliveryapp.courierservice.domain.CourierStatus;
import com.deliveryapp.courierservice.dto.CreateCourierRequest;
import com.deliveryapp.courierservice.dto.UpdateCourierRequest;
import com.deliveryapp.courierservice.exception.CourierBusyException;
import com.deliveryapp.courierservice.exception.CourierNotFoundException;
import com.deliveryapp.courierservice.exception.NoAvailableCourierException;
import com.deliveryapp.courierservice.repository.CourierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public List<Courier> getAll() {
        return courierRepository.findAll();
    }

    public Courier update(UUID id, UpdateCourierRequest request) {
        Courier courier = getById(id);
        courier.setName(request.name());
        courier.setPhone(request.phone());
        return courierRepository.save(courier);
    }

    public void delete(UUID id) {
        Courier courier = getById(id);
        if (courier.getStatus() == CourierStatus.BUSY) {
            throw new CourierBusyException(id);
        }
        courierRepository.delete(courier);
    }

    @Transactional(readOnly = true)
    public Courier getAvailable() {
        return courierRepository.findFirstByStatus(CourierStatus.FREE)
                .orElseThrow(NoAvailableCourierException::new);
    }

    public void markBusy(UUID courierId) {
        Courier courier = getById(courierId);
        courier.setStatus(CourierStatus.BUSY);
        courierRepository.save(courier);
    }

    public void markFree(UUID courierId) {
        Courier courier = getById(courierId);
        courier.setStatus(CourierStatus.FREE);
        courierRepository.save(courier);
    }
}
