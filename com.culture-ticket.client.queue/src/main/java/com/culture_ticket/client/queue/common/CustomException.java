package com.culture_ticket.client.queue.common;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private String result;
    private ErrorType errorType;

    public CustomException(ErrorType errorType) {
        this.result = "ERROR";
        this.errorType = errorType;
    }

    //사용 예시
    // if(user.isEmpty()){
    //    throw new CustomException(ErrorType.NOT_FOUND_USER);
    // }
}
