package com.culture_ticket.client.reservation_payment.infrastructure.client;

import com.culture_ticket.client.reservation_payment.common.ResponseDataDto;
import com.culture_ticket.client.reservation_payment.infrastructure.dto.PerformanceResponseDto;
import com.culture_ticket.client.reservation_payment.infrastructure.dto.SeatResponseDto;
import com.culture_ticket.client.reservation_payment.infrastructure.dto.TimeTableResponseDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "performance", url = "/api/v1")
public interface PerformanceClient {

    @GetMapping("/performances/{performanceId}")
    ResponseDataDto<PerformanceResponseDto> getPerfomance(@PathVariable UUID perfomanceId);

    @GetMapping("/seats/{seatId}")
    ResponseDataDto<SeatResponseDto> getSeat(@PathVariable UUID seatId);

    @GetMapping("/timetables/{timeTableId}")
    ResponseDataDto<TimeTableResponseDto> getTimeTable(@PathVariable UUID timeTableId);
}
