package com.daniilgrebenuk.cinemarestspring.exception;

public class InvalidReservationException extends RuntimeException {

  public InvalidReservationException(String message) {
    super(message);
  }

  public InvalidReservationException(String message, Throwable cause) {
    super(message, cause);
  }
}
