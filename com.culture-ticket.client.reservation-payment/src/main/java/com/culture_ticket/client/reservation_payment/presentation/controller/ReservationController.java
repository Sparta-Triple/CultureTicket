package com.culture_ticket.client.reservation_payment.presentation.controller;

import com.culture_ticket.client.reservation_payment.application.dto.responseDto.ReservationResponseDto;
import com.culture_ticket.client.reservation_payment.application.service.ReservationService;
import com.culture_ticket.client.reservation_payment.common.ResponseDataDto;
import com.culture_ticket.client.reservation_payment.common.ResponseMessageDto;
import com.culture_ticket.client.reservation_payment.common.ResponseStatus;
import com.culture_ticket.client.reservation_payment.common.util.PageableUtil;
import com.culture_ticket.client.reservation_payment.domain.model.Reservation;
import com.querydsl.core.types.Predicate;
import jakarta.ws.rs.Path;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            new ResponseDataDto<>(ResponseStatus.GET_RESERVATION_SUCCESS, responseDto));
    }

    /**
     * 예약 내역 단일 조회
     *
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
        @RequestHeader(value = "X-Role", required = true) String role,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) Sort.Direction direction,
        @RequestParam(required = false) String sort
    ) {
        // TODO: role 검사 (user)

        Pageable pageable = PageableUtil.createPageable(page, size, direction, sort);

        Page<ReservationResponseDto> responseDto = reservationService.getMeReservation(userId,
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
     * @param predicate
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<ResponseDataDto<Page<ReservationResponseDto>>> searchReservation(
        @RequestHeader(value = "X-Role", required = true) String role,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) Sort.Direction direction,
        @RequestParam(required = false) String sort,
        @QuerydslPredicate(root = Reservation.class) Predicate predicate
    ) {
        Pageable pageable = PageableUtil.createPageable(page, size, direction, sort);

        Page<ReservationResponseDto> responseDto = reservationService.searchReservation(pageable,
            predicate);

        return ResponseEntity.ok(
            new ResponseDataDto<>(ResponseStatus.GET_RESERVATION_SUCCESS, responseDto));
    }
}
