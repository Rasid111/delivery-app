package com.deliveryapp.paymentservice.controller;

import com.deliveryapp.paymentservice.domain.CourierBalance;
import com.deliveryapp.paymentservice.domain.Payment;
import com.deliveryapp.paymentservice.dto.CourierBalanceResponse;
import com.deliveryapp.paymentservice.dto.PaymentResponse;
import com.deliveryapp.paymentservice.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getAllPayments() {
        List<PaymentResponse> payments = paymentService.getAll().stream()
                .map(PaymentController::toPaymentResponse)
                .toList();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable UUID id) {
        Payment payment = paymentService.getById(id);
        return ResponseEntity.ok(toPaymentResponse(payment));
    }

    @GetMapping("/couriers/{courierId}/balance")
    public ResponseEntity<CourierBalanceResponse> getBalance(@PathVariable UUID courierId) {
        CourierBalance balance = paymentService.getBalance(courierId);
        return ResponseEntity.ok(toBalanceResponse(balance));
    }

    @GetMapping("/couriers/{courierId}")
    public ResponseEntity<List<PaymentResponse>> getPayments(@PathVariable UUID courierId) {
        List<PaymentResponse> payments = paymentService.getPayments(courierId).stream()
                .map(PaymentController::toPaymentResponse)
                .toList();
        return ResponseEntity.ok(payments);
    }

    private CourierBalanceResponse toBalanceResponse(CourierBalance balance) {
        return new CourierBalanceResponse(
                balance.getCourierId(),
                balance.getBalance(),
                balance.getTurnover(),
                balance.getUpdatedAt()
        );
    }

    private static PaymentResponse toPaymentResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getOrderId(),
                payment.getCourierId(),
                payment.getDeliveryPrice(),
                payment.getEarning(),
                payment.getStatus(),
                payment.getCreatedAt(),
                payment.getUpdatedAt()
        );
    }
}
