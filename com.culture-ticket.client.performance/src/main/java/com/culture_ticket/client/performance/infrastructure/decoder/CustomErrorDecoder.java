package com.culture_ticket.client.performance.infrastructure.decoder;

import com.culture_ticket.client.performance.common.CustomException;
import com.culture_ticket.client.performance.common.ErrorType;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        return switch (response.status()) {
            case 400 -> new CustomException(ErrorType.FEIGN_CLIENT_INVALID_REQUEST);
            case 404 -> new CustomException(ErrorType.FEIGN_CLIENT_RESOURCE_NOT_FOUND);
            case 429 -> new RetryableException(
                response.status(),
                response.reason(),
                response.request().httpMethod(),
                (Long) null,
                response.request()
            );
            default -> new CustomException(ErrorType.FEIGN_CLIENT_UNKNOWN_ERROR);
        };
    }

}
