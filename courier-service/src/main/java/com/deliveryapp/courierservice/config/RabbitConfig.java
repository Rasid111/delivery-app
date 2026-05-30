package com.deliveryapp.courierservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "delivery.events";

    public static final String ROUTING_ORDER_ASSIGNED = "order.assigned";
    public static final String ROUTING_ORDER_DELIVERED = "order.delivered";

    public static final String QUEUE_ORDER_ASSIGNED = "courier.order-assigned";
    public static final String QUEUE_ORDER_DELIVERED = "courier.order-delivered";

    @Bean
    public TopicExchange deliveryEventsExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue orderAssignedQueue() {
        return new Queue(QUEUE_ORDER_ASSIGNED, true);
    }

    @Bean
    public Queue orderDeliveredQueue() {
        return new Queue(QUEUE_ORDER_DELIVERED, true);
    }

    @Bean
    public Binding orderAssignedBinding(Queue orderAssignedQueue, TopicExchange deliveryEventsExchange) {
        return BindingBuilder.bind(orderAssignedQueue).to(deliveryEventsExchange).with(ROUTING_ORDER_ASSIGNED);
    }

    @Bean
    public Binding orderDeliveredBinding(Queue orderDeliveredQueue, TopicExchange deliveryEventsExchange) {
        return BindingBuilder.bind(orderDeliveredQueue).to(deliveryEventsExchange).with(ROUTING_ORDER_DELIVERED);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
