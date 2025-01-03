package com.culture_ticket.client.reservation_payment.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "p_payment")
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "total_price", nullable = false)
    private Long totalPrice;

    @OneToMany(mappedBy = "payment")
    private List<SeatPayment> seatPayments = new ArrayList<>();

    @OneToOne(mappedBy = "payment")
    private Reservation reservation;

    @Builder
    private Payment(Long userId, Long totalPrice) {
        this.userId = userId;
        this.totalPrice = totalPrice;
    }
}
