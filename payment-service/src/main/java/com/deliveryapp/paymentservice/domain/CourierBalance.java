package com.deliveryapp.paymentservice.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "courier_balances")
@Getter
@Setter
@NoArgsConstructor
public class CourierBalance {
    @Id
    @Column(name = "courier_id", nullable = false, updatable = false)
    private UUID courierId;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "turnover", nullable = false)
    private BigDecimal turnover;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourierBalance other)) return false;
        return courierId != null && courierId.equals(other.courierId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(courierId);
    }
}
