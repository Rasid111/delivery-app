package com.deliveryapp.orderservice.event;

import com.deliveryapp.orderservice.config.RabbitConfig;
import com.deliveryapp.orderservice.domain.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public OrderEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishOrderCreated(Order order) {
        OrderCreatedEvent event = new OrderCreatedEvent(
                order.getId(),
                order.getCourierId(),
                order.getPrice(),
                order.getPickupAddress(),
                order.getDeliveryAddress(),
                order.getCreatedAt()
        );
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_ORDER_CREATED, event);
    }

    public void publishOrderAssigned(Order order) {
        OrderAssignedEvent event = new OrderAssignedEvent(
                order.getId(),
                order.getCourierId(),
                order.getUpdatedAt()
        );
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_ORDER_ASSIGNED, event);
    }

    public void publishOrderDelivered(Order order) {
        OrderDeliveredEvent event = new OrderDeliveredEvent(
                order.getId(),
                order.getCourierId(),
                order.getPrice(),
                order.getUpdatedAt()
        );
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_ORDER_DELIVERED, event);
    }

    public void publishOrderCancelled(Order order) {
        OrderCancelledEvent event = new OrderCancelledEvent(
                order.getId(),
                order.getCourierId()
        );
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_ORDER_CANCELLED, event);
    }
}