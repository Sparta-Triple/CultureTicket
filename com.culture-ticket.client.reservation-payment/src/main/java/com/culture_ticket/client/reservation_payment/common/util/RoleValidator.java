package com.culture_ticket.client.reservation_payment.common.util;

import com.culture_ticket.client.reservation_payment.common.CustomException;
import com.culture_ticket.client.reservation_payment.common.ErrorType;

public class RoleValidator {
    public static void validateIsAdmin(String role) {
        if (!role.equals("ADMIN")) {
            throw new CustomException(ErrorType.ACCESS_DENIED);
        }
    }

    public static void validateIsUser(String role) {
        if (!role.equals("USER")) {
            throw new CustomException(ErrorType.ACCESS_DENIED);
        }
    }

    public static void validateIsAdminOrUser(String role) {
        if (!(role.equals("ADMIN") || role.equals("USER"))) {
            throw new CustomException(ErrorType.ACCESS_DENIED);
        }
    }
}
