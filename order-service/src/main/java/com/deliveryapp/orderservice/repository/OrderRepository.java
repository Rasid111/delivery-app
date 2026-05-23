package com.deliveryapp.orderservice.repository;

import com.deliveryapp.orderservice.domain.Order;
import com.deliveryapp.orderservice.domain.OrderStatus;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByCourierId(UUID courierId);
    List<Order> findByStatus(OrderStatus status);
}
