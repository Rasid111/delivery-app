package com.deliveryapp.orderservice.service;

import com.deliveryapp.orderservice.dto.CreateOrderRequest;
import com.deliveryapp.orderservice.domain.Order;
import com.deliveryapp.orderservice.domain.OrderStatus;
import com.deliveryapp.orderservice.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Order createOrder(CreateOrderRequest request) {
        Order order = new Order();
        // TODO: get real courier id
        order.setCourierId(UUID.randomUUID());
        order.setPickupAddress(request.pickupAddress());
        order.setDeliveryAddress(request.deliveryAddress());
        // TODO: calculate real price
        order.setPrice(BigDecimal.valueOf(new Random().nextInt(100)));
        order.setStatus(OrderStatus.ASSIGNED);

        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public Order getById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + id));
    }
}