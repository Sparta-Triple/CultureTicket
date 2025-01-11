package com.culture_ticket.client.reservation_payment.infrastructure.client;

import com.culture_ticket.client.reservation_payment.domain.model.SeatStatus;
import com.culture_ticket.client.reservation_payment.infrastructure.dto.FeignClientResponseDataDto;
import com.culture_ticket.client.reservation_payment.infrastructure.dto.FeignClientResponseMessageDto;
import com.culture_ticket.client.reservation_payment.infrastructure.dto.PerformanceResponseDto;
import com.culture_ticket.client.reservation_payment.infrastructure.dto.SeatResponseDto;
import com.culture_ticket.client.reservation_payment.infrastructure.dto.TimeTableResponseDto;
import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "performance-service")
public interface PerformanceClient {

    @GetMapping("/api/v1/performances/info/{performanceId}")
    FeignClientResponseDataDto<PerformanceResponseDto> getPerformance(@PathVariable UUID performanceId);

    @GetMapping("/api/v1/seats/{seatId}")
    FeignClientResponseDataDto<SeatResponseDto> getSeat(@PathVariable UUID seatId);

    @GetMapping("/api/v1/timetables/{timeTableId}")
    FeignClientResponseDataDto<TimeTableResponseDto> getTimeTable(@PathVariable UUID timeTableId);

    @PutMapping("/api/v1/seats/status/{seatStatus}")
    FeignClientResponseMessageDto updateSeatsStatusAvailable(
        @RequestHeader(value = "X-User-Name") String username,
        @PathVariable SeatStatus seatStatus,
        @RequestBody List<UUID> seatIds);
}
