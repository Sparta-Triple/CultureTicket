package com.culture_ticket.client.reservation_payment.infrastructure.client;

import com.culture_ticket.client.reservation_payment.common.ResponseDataDto;
import com.culture_ticket.client.reservation_payment.domain.model.Performance;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "perfomance", url = "/api/v1/performances")
public interface PerformanceClient {

    @GetMapping("/{performanceId}")
    ResponseDataDto<Performance> getPerfomance(@PathVariable UUID perfomanceId);
}
