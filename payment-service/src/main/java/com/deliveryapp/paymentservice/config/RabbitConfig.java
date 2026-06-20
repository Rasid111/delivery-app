package com.deliveryapp.paymentservice.config;

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

    public static final String ROUTING_ORDER_CREATED = "order.created";
    public static final String ROUTING_ORDER_DELIVERED = "order.delivered";
    public static final String ROUTING_ORDER_CANCELLED = "order.cancelled";

    public static final String QUEUE_ORDER_CREATED = "payment.order-created";
    public static final String QUEUE_ORDER_DELIVERED = "payment.order-delivered";
    public static final String QUEUE_ORDER_CANCELLED = "payment.order-cancelled";

    @Bean
    public TopicExchange deliveryEventsExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue orderCreatedQueue() {
        return new Queue(QUEUE_ORDER_CREATED, true);
    }

    @Bean
    public Queue orderDeliveredQueue() {
        return new Queue(QUEUE_ORDER_DELIVERED, true);
    }

    @Bean
    public Queue orderCancelledQueue() {
        return new Queue(QUEUE_ORDER_CANCELLED, true);
    }

    @Bean
    public Binding orderCreatedBinding(Queue orderCreatedQueue, TopicExchange deliveryEventsExchange) {
        return BindingBuilder.bind(orderCreatedQueue).to(deliveryEventsExchange).with(ROUTING_ORDER_CREATED);
    }

    @Bean
    public Binding orderDeliveredBinding(Queue orderDeliveredQueue, TopicExchange deliveryEventsExchange) {
        return BindingBuilder.bind(orderDeliveredQueue).to(deliveryEventsExchange).with(ROUTING_ORDER_DELIVERED);
    }

    @Bean
    public Binding orderCancelledBinding(Queue orderCancelledQueue, TopicExchange deliveryEventsExchange) {
        return BindingBuilder.bind(orderCancelledQueue).to(deliveryEventsExchange).with(ROUTING_ORDER_CANCELLED);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
