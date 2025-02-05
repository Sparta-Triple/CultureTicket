package com.culture_ticket.client.coupon.common.util;


import com.culture_ticket.client.coupon.common.CustomException;
import com.culture_ticket.client.coupon.common.ErrorType;

public class RoleValidator {

  private final static String ADMIN = "ADMIN";
  private final static String USER = "USER";

  public static void validateIsAdmin(String role){
    if (!role.equals(ADMIN)){
      throw new CustomException(ErrorType.ACCESS_DENIED);
    }
  }

  public static void validateIsUSER(String role){
    if (!role.equals(USER) && !role.equals(ADMIN)){
      throw new CustomException(ErrorType.ACCESS_DENIED);
    }
  }
}
