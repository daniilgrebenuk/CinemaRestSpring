package com.daniilgrebenuk.cinemarestspring.util;

public interface GlobalConstants {

  String CUSTOMER_NAME_REGEX = "[A-ZŻŹĆĄŚĘŁÓŃ][a-zżźćńółęąś]+";
  String CUSTOMER_SURNAME_REGEX = "[A-ZŻŹĆĄŚĘŁÓŃ][a-zżźćńółęąś]+(-[A-ZŻŹĆĄŚĘŁÓŃ][a-zżźćńółęąś]+)?";

  String LOCAL_DATE_TIME_PATTERN = "yyyy-MM-dd'T'H:mm";

  long EXPIRATION_TIME_IN_HOURS = 2;
}
