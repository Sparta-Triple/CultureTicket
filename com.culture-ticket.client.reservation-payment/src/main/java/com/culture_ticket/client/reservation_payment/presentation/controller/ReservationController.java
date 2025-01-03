package com.culture_ticket.client.reservation_payment.presentation.controller;

import com.culture_ticket.client.reservation_payment.application.dto.responseDto.ReservationResponseDto;
import com.culture_ticket.client.reservation_payment.application.service.ReservationService;
import com.culture_ticket.client.reservation_payment.common.ResponseDataDto;
import com.culture_ticket.client.reservation_payment.common.ResponseStatus;
import com.culture_ticket.client.reservation_payment.common.util.PageableUtil;
import feign.Response;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    /**
     * 예약 내역 전체 조회
     *
     * @param userId
     * @param role
     * @param page
     * @param size
     * @param direction
     * @param sort
     * @return
     */
    @GetMapping
    public ResponseEntity<ResponseDataDto<Page<ReservationResponseDto>>> getReservations(
        @RequestHeader(value = "X-User-Id", required = true) String userId,
        @RequestHeader(value = "X-Role", required = true) String role,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) Sort.Direction direction,
        @RequestParam(required = false) String sort
    ) {
        // TODO: role 검사 (admin)

        Pageable pageable = PageableUtil.createPageable(page, size, direction, sort);

        Page<ReservationResponseDto> responseDto = reservationService.getReservations(pageable);

        return ResponseEntity.ok(
            new ResponseDataDto<>(ResponseStatus.RESERVATION_GET_SUCCESS, responseDto));
    }

    /**
     * 예약 내역 단일 조회
     *
     * @param userId
     * @param role
     * @param reservationId
     * @return
     */
    @GetMapping("/{reservationId}")
    public ResponseEntity<ResponseDataDto<ReservationResponseDto>> getReservation(
        @RequestHeader(value = "X-Role", required = true) String role,
        @PathVariable UUID reservationId
    ) {
        // TODO: role 검사 (admin, user)

        ReservationResponseDto responseDto = reservationService.getReservation(reservationId);

        return ResponseEntity.ok(
            new ResponseDataDto<>(ResponseStatus.RESERVATION_GET_SUCCESS, responseDto));
    }

    /**
     * 내 예약 내역 조회
     * @param userId
     * @param role
     * @param page
     * @param size
     * @param direction
     * @param sort
     * @return
     */
    @GetMapping("/me")
    public ResponseEntity<ResponseDataDto<Page<ReservationResponseDto>>> getMeReservation(
        @RequestHeader(value = "X-User-Id", required = true) String userId,
        @RequestHeader(value = "X-Role", required = true) String role,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) Sort.Direction direction,
        @RequestParam(required = false) String sort
    ) {
        // TODO: role 검사 (user)

        Pageable pageable = PageableUtil.createPageable(page, size, direction, sort);

        Page<ReservationResponseDto> responseDto = reservationService.getMeReservation(userId, pageable);

        return ResponseEntity.ok(
            new ResponseDataDto<>(ResponseStatus.RESERVATION_GET_SUCCESS, responseDto));
    }
}
