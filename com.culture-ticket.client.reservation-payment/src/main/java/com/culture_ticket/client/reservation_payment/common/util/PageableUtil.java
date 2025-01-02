package com.culture_ticket.client.reservation_payment.common.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

public class PageableUtil {

    private static final List<Integer> ALLOWED_PAGE_SIZES = Arrays.asList(10, 30, 50);
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final Sort.Direction DEFAULT_DIRECTION = Sort.Direction.DESC;
    private static final String DEFAULT_SORT = "createdAt";

    public static Pageable createPageable(int page, int size, Sort.Direction direction, String sort) {
        if (!ALLOWED_PAGE_SIZES.contains(size)) {
            size = DEFAULT_PAGE_SIZE; // 허용되지 않는 사이즈는 기본값으로 설정
        }

        if (direction == null) {
            direction = DEFAULT_DIRECTION; // 기본 방향 설정
        }

        if (sort == null || sort.isEmpty()) {
            sort = DEFAULT_SORT; // 기본 정렬 필드 설정
        }

        return PageRequest.of(page, size, direction, sort);
    }
}
