package com.culture_ticket.client.queue.application.service;

import com.culture_ticket.client.queue.common.CustomException;
import com.culture_ticket.client.queue.common.ErrorType;
import com.culture_ticket.client.queue.domain.medel.WaitingQueue;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class WaitingQueueValidator {

    public WaitingQueue checkSavedQueue(Optional<WaitingQueue> waitingQueue) {

        if (waitingQueue.isEmpty()) {
            throw new CustomException(ErrorType.TOKEN_IS_FAILED);
        }

        return waitingQueue.get();
    }
}
