package com.culture_ticket.client.reservation_payment.infrastructure.client;

import com.culture_ticket.client.reservation_payment.common.ResponseDataDto;
import com.culture_ticket.client.reservation_payment.domain.model.Performance;
import com.culture_ticket.client.reservation_payment.domain.model.Seat;
import com.culture_ticket.client.reservation_payment.domain.model.TimeTable;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "performance", url = "/api/v1")
public interface PerformanceClient {

    @GetMapping("/performances/{performanceId}")
    ResponseDataDto<Performance> getPerfomance(@PathVariable UUID perfomanceId);

    @GetMapping("/seats/{seatId}")
    ResponseDataDto<Seat> getSeat(@PathVariable UUID seatId);

    @GetMapping("/timetables/{timeTableId}")
    ResponseDataDto<TimeTable> getTimeTable(@PathVariable UUID timeTableId);
}
