package com.culture_ticket.client.performance.common.util;


public enum RedisKeyType {
    PERFORMANCE_INFO("performance:detail:%s"),
    PERFORMANCE_VIEW_RANK("performance:rank:view");

    private final String keyPattern;

    RedisKeyType(String keyPattern) {
        this.keyPattern = keyPattern;
    }

    public String format(Object... args) {
        return String.format(keyPattern, args);
    }
}