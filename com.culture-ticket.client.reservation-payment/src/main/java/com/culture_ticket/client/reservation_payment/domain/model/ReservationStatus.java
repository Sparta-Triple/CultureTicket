package com.culture_ticket.client.reservation_payment.domain.model;

public enum ReservationStatus {
    CONFIRMED("확인"),
    CANCELED("취소");

    private final String description;

    ReservationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
