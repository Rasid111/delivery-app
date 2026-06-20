package com.deliveryapp.notificationservice.event;

import com.deliveryapp.notificationservice.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderEventListener {

    @RabbitListener(queues = RabbitConfig.QUEUE_ORDER_CREATED)
    public void onOrderCreated(OrderCreatedEvent event) {
        log.info("Order created: order {} assigned to courier {}", event.orderId(), event.courierId());
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_ORDER_ASSIGNED)
    public void onOrderAssigned(OrderAssignedEvent event) {
        log.info("Courier assigned: courier {} -> order {}", event.courierId(), event.orderId());
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_ORDER_DELIVERED)
    public void onOrderDelivered(OrderDeliveredEvent event) {
        log.info("Order delivered: order {} by courier {}", event.orderId(), event.courierId());
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_ORDER_CANCELLED)
    public void onOrderCancelled(OrderCancelledEvent event) {
        log.info("Order cancelled: order {} (courier {} released)", event.orderId(), event.courierId());
    }
}
