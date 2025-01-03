package com.culture_ticket.client.reservation_payment.application.service;

import com.culture_ticket.client.reservation_payment.application.dto.ResGetReservationDto;
import com.culture_ticket.client.reservation_payment.domain.model.Reservation;
import com.culture_ticket.client.reservation_payment.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    /**
     * 예약 내역 전체 조회
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Page<ResGetReservationDto> getReservation(Pageable pageable) {
        Page<Reservation> reservationPage = reservationRepository.findAll(pageable);
        return reservationPage.map(ResGetReservationDto::from); // TODO: of로 만들어서 User feignClient로 받아와야하고, 좌석ID타고 공연, 타임테이블 가져와야하고
    }

}
