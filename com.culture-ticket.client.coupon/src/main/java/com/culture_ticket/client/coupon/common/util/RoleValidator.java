package com.culture_ticket.client.coupon.common.util;

import com.culture_ticket.client.performance.common.CustomException;
import com.culture_ticket.client.performance.common.ErrorType;

public class RoleValidator {

  private final static String ADMIN = "ADMIN";
  private final static String USER = "USER";

  public static void validateIsAdmin(String role){
    if (!role.equals(ADMIN)){
      throw new CustomException(ErrorType.ROLE_UNAUTHORIZED);
    }
  }

  public static void validateIsUSER(String role){
    if (!role.equals(USER)){
      throw new CustomException(ErrorType.ROLE_UNAUTHORIZED);
    }
  }

}
