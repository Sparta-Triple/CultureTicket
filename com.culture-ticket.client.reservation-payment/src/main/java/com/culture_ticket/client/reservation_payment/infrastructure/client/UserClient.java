package com.culture_ticket.client.reservation_payment.infrastructure.client;

import com.culture_ticket.client.reservation_payment.infrastructure.dto.FeignClientResponseDataDto;
import com.culture_ticket.client.reservation_payment.infrastructure.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/v1/users/info/{userId}")
    FeignClientResponseDataDto<UserResponseDto> getUser(@PathVariable Long userId);
}
