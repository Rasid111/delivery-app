package com.deliveryapp.paymentservice.service;

import com.deliveryapp.paymentservice.domain.CourierBalance;
import com.deliveryapp.paymentservice.domain.Payment;
import com.deliveryapp.paymentservice.domain.PaymentStatus;
import com.deliveryapp.paymentservice.event.OrderCreatedEvent;
import com.deliveryapp.paymentservice.event.OrderDeliveredEvent;
import com.deliveryapp.paymentservice.exception.CourierBalanceNotFoundException;
import com.deliveryapp.paymentservice.exception.PaymentNotFoundException;
import com.deliveryapp.paymentservice.repository.CourierBalanceRepository;
import com.deliveryapp.paymentservice.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class PaymentService {

    /** Courier keeps this share of the delivery price as earning. */
    private static final BigDecimal EARNING_RATE = new BigDecimal("0.80");

    private final PaymentRepository paymentRepository;
    private final CourierBalanceRepository courierBalanceRepository;

    /**
     * On ORDER_CREATED: open a PENDING payment record.
     * Idempotent — RabbitMQ delivers at-least-once, so a payment for an orderId is created only once.
     */
    public void onOrderCreated(OrderCreatedEvent event) {
        if (paymentRepository.existsByOrderId(event.orderId())) {
            log.info("Payment for order {} already exists, skipping ORDER_CREATED", event.orderId());
            return;
        }

        Payment payment = new Payment();
        payment.setOrderId(event.orderId());
        payment.setCourierId(event.courierId());
        payment.setDeliveryPrice(event.price());
        payment.setStatus(PaymentStatus.PENDING);
        paymentRepository.save(payment);
    }

    /**
     * On ORDER_DELIVERED: calculate the courier earning, credit balance & turnover, mark payment COMPLETED.
     * Idempotent — a payment already COMPLETED is not processed again.
     */
    public void onOrderDelivered(OrderDeliveredEvent event) {
        Payment payment = paymentRepository.findByOrderId(event.orderId())
                .orElseGet(() -> {
                    // ORDER_DELIVERED arrived without a prior ORDER_CREATED — open the record now.
                    Payment p = new Payment();
                    p.setOrderId(event.orderId());
                    p.setCourierId(event.courierId());
                    p.setDeliveryPrice(event.price());
                    p.setStatus(PaymentStatus.PENDING);
                    return p;
                });

        if (payment.getStatus() == PaymentStatus.COMPLETED) {
            log.info("Payment for order {} already COMPLETED, skipping ORDER_DELIVERED", event.orderId());
            return;
        }

        BigDecimal earning = payment.getDeliveryPrice().multiply(EARNING_RATE);
        payment.setEarning(earning);
        payment.setStatus(PaymentStatus.COMPLETED);
        paymentRepository.save(payment);

        creditCourier(event.courierId(), earning);
    }

    private void creditCourier(UUID courierId, BigDecimal earning) {
        CourierBalance balance = courierBalanceRepository.findById(courierId)
                .orElseGet(() -> {
                    CourierBalance b = new CourierBalance();
                    b.setCourierId(courierId);
                    b.setBalance(BigDecimal.ZERO);
                    b.setTurnover(BigDecimal.ZERO);
                    return b;
                });
        balance.setBalance(balance.getBalance().add(earning));
        balance.setTurnover(balance.getTurnover().add(earning));
        courierBalanceRepository.save(balance);
    }

    @Transactional(readOnly = true)
    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Payment getById(UUID id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public CourierBalance getBalance(UUID courierId) {
        return courierBalanceRepository.findById(courierId)
                .orElseThrow(() -> new CourierBalanceNotFoundException(courierId));
    }

    @Transactional(readOnly = true)
    public List<Payment> getPayments(UUID courierId) {
        return paymentRepository.findByCourierIdOrderByCreatedAtDesc(courierId);
    }
}
