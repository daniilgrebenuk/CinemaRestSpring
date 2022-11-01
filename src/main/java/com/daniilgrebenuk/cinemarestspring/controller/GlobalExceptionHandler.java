package com.daniilgrebenuk.cinemarestspring.controller;


import com.daniilgrebenuk.cinemarestspring.exception.DataNotFoundException;
import com.daniilgrebenuk.cinemarestspring.exception.InvalidReservationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {


  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleInvalidReservationException(MethodArgumentNotValidException ex) {
    return ResponseEntity.badRequest().body(
        ex.getBindingResult()
            .getAllErrors()
            .stream()
            .collect(Collectors.toMap(
                error -> ((FieldError) error).getField(),
                error -> Optional.ofNullable(error.getDefaultMessage()).orElse("No error specified!"),
                (m1, m2) -> m1 + ", " + m2
            ))
    );
  }

  @ExceptionHandler( InvalidReservationException.class )
  public ResponseEntity<Map<String, String>> handleInvalidReservationException(RuntimeException ex) {
    return ResponseEntity.badRequest().body(Map.of("errorMessage", ex.getMessage()));
  }

  @ExceptionHandler(DataNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleDataNotFoundException(RuntimeException ex) {
    return new ResponseEntity<>(Map.of("errorMessage", ex.getMessage()), HttpStatus.NOT_FOUND);
  }


}
