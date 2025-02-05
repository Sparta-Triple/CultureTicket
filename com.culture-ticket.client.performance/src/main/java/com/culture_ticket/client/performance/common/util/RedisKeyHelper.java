package com.culture_ticket.client.performance.common.util;

import java.util.UUID;

public class RedisKeyHelper {

    private RedisKeyHelper() {
        // Prevent instantiation
    }

    // 매개변수로 받은 id값의 event의 details의 키를 반환.
    public static String getPerformanceDetailsKey(UUID performanceId) {
        return RedisKeyType.PERFORMANCE_INFO.format(performanceId);
    }

    //
    public static String getViewRankKey() {
        return RedisKeyType.PERFORMANCE_VIEW_RANK.format();
    }
}