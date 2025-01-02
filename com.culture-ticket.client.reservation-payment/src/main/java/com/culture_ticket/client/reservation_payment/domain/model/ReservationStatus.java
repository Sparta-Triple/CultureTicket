package com.culture_ticket.client.reservation_payment.domain.model;

public enum ReservationStatus {
    CONFIRM("확인"),
    CANCELL("취소");

    private final String description;

    ReservationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}