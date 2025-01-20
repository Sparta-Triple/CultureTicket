package com.culture_ticket.client.queue.domain.repository;

import com.culture_ticket.client.queue.domain.medel.WaitingQueue;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface WaitingQueueRepository {

    Optional<WaitingQueue> saveQueue(WaitingQueue queue);

    long getActiveCnt();

    Set<String> getWaitingTokens();

    void saveActiveQueue(Long userId, String token);

    void deleteWaitingQueue(Long userId, String token);

    Long getMyWaitingNum(Long userId, String token);

    void saveWaitingQueue(Long userId, String token);

    void deleteWaitingTokens();

    void saveActiveQueues(Set<String> waitingTokens);

    void deleteExpiredToken(String token);

    void setTimeout(String key, long timeout, TimeUnit unit);
}
