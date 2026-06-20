package com.deliveryapp.paymentservice.event;

import com.deliveryapp.paymentservice.config.RabbitConfig;
import com.deliveryapp.paymentservice.service.PaymentService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    private final PaymentService paymentService;

    public OrderEventListener(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_ORDER_CREATED)
    public void onOrderCreated(OrderCreatedEvent event) {
        paymentService.onOrderCreated(event);
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_ORDER_DELIVERED)
    public void onOrderDelivered(OrderDeliveredEvent event) {
        paymentService.onOrderDelivered(event);
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_ORDER_CANCELLED)
    public void onOrderCancelled(OrderCancelledEvent event) {
        paymentService.onOrderCancelled(event);
    }
}
