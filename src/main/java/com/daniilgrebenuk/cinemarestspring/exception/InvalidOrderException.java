package com.daniilgrebenuk.cinemarestspring.exception;

public class InvalidOrderException extends RuntimeException{

  public InvalidOrderException(String message) {
    super(message);
  }

  public InvalidOrderException(String message, Throwable cause) {
    super(message, cause);
  }
}