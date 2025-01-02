package com.culture_ticket.client.reservation_payment.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "performance")
public interface SeatClient {

}
