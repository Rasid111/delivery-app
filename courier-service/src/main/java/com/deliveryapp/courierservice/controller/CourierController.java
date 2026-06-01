package com.deliveryapp.courierservice.controller;

import com.deliveryapp.courierservice.domain.Courier;
import com.deliveryapp.courierservice.dto.AvailableCourierResponse;
import com.deliveryapp.courierservice.dto.CourierResponse;
import com.deliveryapp.courierservice.dto.CreateCourierRequest;
import com.deliveryapp.courierservice.dto.UpdateCourierRequest;
import com.deliveryapp.courierservice.service.CourierService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/couriers")
public class CourierController {

    private final CourierService courierService;

    public CourierController(CourierService courierService) {
        this.courierService = courierService;
    }

    @PostMapping
    public ResponseEntity<CourierResponse> createCourier(@Valid @RequestBody CreateCourierRequest request) {
        Courier courier = courierService.create(request);
        CourierResponse response = toResponse(courier);
        URI location = URI.create("/api/couriers/" + courier.getId());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CourierResponse>> getCouriers() {
        List<CourierResponse> couriers = courierService.getAll().stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(couriers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourierResponse> getCourier(@PathVariable UUID id) {
        Courier courier = courierService.getById(id);
        return ResponseEntity.ok(toResponse(courier));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourierResponse> updateCourier(@PathVariable UUID id,
                                                         @Valid @RequestBody UpdateCourierRequest request) {
        Courier courier = courierService.update(id, request);
        return ResponseEntity.ok(toResponse(courier));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourier(@PathVariable UUID id) {
        courierService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available")
    public ResponseEntity<AvailableCourierResponse> getAvailableCourier() {
        Courier courier = courierService.getAvailable();
        return ResponseEntity.ok(new AvailableCourierResponse(courier.getId(), courier.getName()));
    }

    private CourierResponse toResponse(Courier courier) {
        return new CourierResponse(
                courier.getId(),
                courier.getName(),
                courier.getPhone(),
                courier.getStatus(),
                courier.getCreatedAt(),
                courier.getUpdatedAt()
        );
    }
}
