package com.culture_ticket.client.ticket.common.util;

import com.culture_ticket.client.ticket.common.CustomException;
import com.culture_ticket.client.ticket.common.ErrorType;

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

