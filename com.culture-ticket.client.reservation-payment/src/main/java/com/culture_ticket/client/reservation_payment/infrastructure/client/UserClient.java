package com.culture_ticket.client.reservation_payment.infrastructure.client;

import com.culture_ticket.client.reservation_payment.application.dto.feignclient.FeignClientResponseDataDto;
import com.culture_ticket.client.reservation_payment.application.dto.feignclient.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/v1/users/info/{userId}")
    FeignClientResponseDataDto<UserResponseDto> getUserInfo(@PathVariable Long userId);
}
