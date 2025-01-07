package com.culture_ticket.client.reservation_payment.infrastructure.client;

import com.culture_ticket.client.reservation_payment.infrastructure.dto.FeignClientResponseDataDto;
import com.culture_ticket.client.reservation_payment.infrastructure.dto.PerformanceResponseDto;
import com.culture_ticket.client.reservation_payment.infrastructure.dto.SeatResponseDto;
import com.culture_ticket.client.reservation_payment.infrastructure.dto.TimeTableResponseDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "performance-service")
public interface PerformanceClient {

    @GetMapping("/api/v1/performances/{performanceId}")
    FeignClientResponseDataDto<PerformanceResponseDto> getPerfomance(@PathVariable UUID performanceId);

    @GetMapping("/api/v1/seats/{seatId}")
    FeignClientResponseDataDto<SeatResponseDto> getSeat(@PathVariable UUID seatId);

    @GetMapping("/api/v1/timetables/{timeTableId}")
    FeignClientResponseDataDto<TimeTableResponseDto> getTimeTable(@PathVariable UUID timeTableId);
}
