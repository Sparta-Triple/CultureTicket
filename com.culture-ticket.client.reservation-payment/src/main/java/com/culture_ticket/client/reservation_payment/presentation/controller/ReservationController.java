package com.culture_ticket.client.reservation_payment.presentation.controller;

import com.culture_ticket.client.reservation_payment.application.dto.responseDto.RefundPriceResponseDto;
import com.culture_ticket.client.reservation_payment.application.dto.responseDto.ReservationResponseDto;
import com.culture_ticket.client.reservation_payment.application.service.ReservationService;
import com.culture_ticket.client.reservation_payment.common.ResponseDataDto;
import com.culture_ticket.client.reservation_payment.common.ResponseStatus;
import com.culture_ticket.client.reservation_payment.common.util.PageableUtil;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final PageableUtil pageableUtil;

    /**
     * 예약 내역 전체 조회
     *
     * @param role
     * @param page
     * @param size
     * @param direction
     * @param sort
     * @return
     */
    @GetMapping
    public ResponseEntity<ResponseDataDto<Page<ReservationResponseDto>>> getReservations(
        @RequestHeader(value = "X-User-Role", required = true) String role,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) Sort.Direction direction,
        @RequestParam(required = false) String sort
    ) {
        Pageable pageable = pageableUtil.createPageable(page, size, direction, sort);

        Page<ReservationResponseDto> responseDto = reservationService.
            getReservations(role, pageable);

        return ResponseEntity.ok(
            new ResponseDataDto<>(ResponseStatus.GET_RESERVATION_SUCCESS, responseDto));
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
        @RequestHeader(value = "X-User-Id") String userId,
        @RequestHeader(value = "X-User-Role", required = true) String role,
        @PathVariable UUID reservationId
    ) {
        ReservationResponseDto responseDto = reservationService.
            getReservation(userId, role, reservationId);

        return ResponseEntity.ok(
            new ResponseDataDto<>(ResponseStatus.GET_RESERVATION_SUCCESS, responseDto));
    }

    /**
     * 내 예약 내역 조회
     *
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
        @RequestHeader(value = "X-User-Role", required = true) String role,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) Sort.Direction direction,
        @RequestParam(required = false) String sort
    ) {
        Pageable pageable = pageableUtil.createPageable(page, size, direction, sort);

        Page<ReservationResponseDto> responseDto = reservationService.getMeReservation(userId, role,
            pageable);

        return ResponseEntity.ok(
            new ResponseDataDto<>(ResponseStatus.GET_RESERVATION_SUCCESS, responseDto));
    }

    /**
     * 예약 내역 검색
     *
     * @param role
     * @param page
     * @param size
     * @param direction
     * @param sort
     * @param dateRange
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<ResponseDataDto<Page<ReservationResponseDto>>> searchReservation(
        @RequestHeader(value = "X-User-Id", required = true) String userId,
        @RequestHeader(value = "X-User-Role", required = true) String role,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) Sort.Direction direction,
        @RequestParam(required = false) String sort,
        @RequestParam(required = false, defaultValue = "1month") String dateRange
    ) {
        Pageable pageable = pageableUtil.createPageable(page, size, direction, sort);

        Page<ReservationResponseDto> responseDto = reservationService.
            searchReservation(userId, role, pageable, dateRange);

        return ResponseEntity.ok(
            new ResponseDataDto<>(ResponseStatus.GET_RESERVATION_SUCCESS, responseDto));
    }

    /**
     * 예약 취소(삭제)
     *
     * @param userId
     * @param role
     * @param reservationId
     * @return
     */
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<ResponseDataDto<RefundPriceResponseDto>> deleteReservation(
        @RequestHeader(value = "X-User-Id") String userId,
        @RequestHeader(value = "X-User-Name", required = true) String username,
        @RequestHeader(value = "X-User-Role", required = true) String role,
        @PathVariable UUID reservationId
    ) {
        RefundPriceResponseDto responseDto = reservationService.
            deleteReservation(userId, username, role, reservationId);

        return ResponseEntity.ok(
            new ResponseDataDto<>(ResponseStatus.DELETE_RESERVATION_SUCCESS, responseDto));
    }
}
