package com.deliveryapp.courierservice.event;

import com.deliveryapp.courierservice.config.RabbitConfig;
import com.deliveryapp.courierservice.service.CourierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    private final CourierService courierService;

    public OrderEventListener(CourierService courierService) {
        this.courierService = courierService;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_ORDER_ASSIGNED)
    public void onOrderAssigned(OrderAssignedEvent event) {
        courierService.markBusy(event.courierId());
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_ORDER_DELIVERED)
    public void onOrderDelivered(OrderDeliveredEvent event) {
        courierService.markFree(event.courierId());
    }
}
