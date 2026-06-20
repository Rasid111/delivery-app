package com.deliveryapp.orderservice.service;

import com.deliveryapp.orderservice.client.CourierClient;
import com.deliveryapp.orderservice.client.dto.AvailableCourierResponse;
import com.deliveryapp.orderservice.dto.CreateOrderRequest;
import com.deliveryapp.orderservice.domain.Order;
import com.deliveryapp.orderservice.domain.OrderStatus;
import com.deliveryapp.orderservice.event.OrderEventPublisher;
import com.deliveryapp.orderservice.exception.IllegalOrderTransitionException;
import com.deliveryapp.orderservice.exception.NoAvailableCourierException;
import com.deliveryapp.orderservice.exception.OrderNotCancellableException;
import com.deliveryapp.orderservice.exception.OrderNotFoundException;
import com.deliveryapp.orderservice.repository.OrderRepository;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderEventPublisher eventPublisher;
    private final CourierClient courierClient;

    public Order createOrder(CreateOrderRequest request) {
        AvailableCourierResponse courier;
        try {
            courier = courierClient.getAvailable();
        } catch (FeignException.NotFound e) {
            throw new NoAvailableCourierException();
        }

        Order order = new Order();
        order.setCourierId(courier.id());
        order.setPickupAddress(request.pickupAddress());
        order.setDeliveryAddress(request.deliveryAddress());
        // TODO: calculate real price
        order.setPrice(BigDecimal.valueOf(new Random().nextInt(100)));
        order.setStatus(OrderStatus.ASSIGNED);

        Order saved = orderRepository.save(order);

        eventPublisher.publishOrderCreated(saved);
        eventPublisher.publishOrderAssigned(saved);

        return saved;
    }

    @Transactional(readOnly = true)
    public Order getById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public void delete(UUID id) {
        Order order = getById(id);
        if (order.getStatus().isTerminal()) {
            throw new OrderNotCancellableException(order.getStatus());
        }
        orderRepository.delete(order);
        eventPublisher.publishOrderCancelled(order);
    }

    public Order pickupOrder(UUID id) {
        Order order = getById(id);
        transitionStatus(order, OrderStatus.PICKED_UP);
        return order;
    }

    public Order deliverOrder(UUID id) {
        Order order = getById(id);
        transitionStatus(order, OrderStatus.DELIVERED);
        eventPublisher.publishOrderDelivered(order);
        return order;
    }

    private void transitionStatus(Order order, OrderStatus target) {
        if (!order.getStatus().canTransitionTo(target)) {
            throw new IllegalOrderTransitionException(order.getStatus(), target);
        }
        order.setStatus(target);
        orderRepository.save(order);
    }
}