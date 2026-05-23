package com.deliveryapp.orderservice.controller;

import com.deliveryapp.orderservice.dto.CreateOrderRequest;
import com.deliveryapp.orderservice.dto.OrderResponse;
import com.deliveryapp.orderservice.domain.Order;
import com.deliveryapp.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        Order order = orderService.createOrder(request);
        OrderResponse response = new OrderResponse(
                order.getId(),
                order.getCourierId(),
                order.getPickupAddress(),
                order.getDeliveryAddress(),
                order.getPrice(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
        URI location = URI.create("/api/orders/" + order.getId());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable UUID id) {
        Order order = orderService.getById(id);
        OrderResponse response = new OrderResponse(
                order.getId(),
                order.getCourierId(),
                order.getPickupAddress(),
                order.getDeliveryAddress(),
                order.getPrice(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
        return ResponseEntity.ok(response);
    }
}