package com.deliveryapp.paymentservice.repository;

import com.deliveryapp.paymentservice.domain.CourierBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourierBalanceRepository extends JpaRepository<CourierBalance, UUID> {
}
