package com.daniilgrebenuk.cinemarestspring.util;

public interface NamingRegexConstants {

  String CUSTOMER_NAME_REGEX = "[A-ZŻŹĆĄŚĘŁÓŃ][a-zżźćńółęąś]+";
  String CUSTOMER_SURNAME_REGEX = "[A-ZŻŹĆĄŚĘŁÓŃ][a-zżźćńółęąś]+(-[A-ZŻŹĆĄŚĘŁÓŃ][a-zżźćńółęąś]+)?";
}
