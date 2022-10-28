package com.daniilgrebenuk.cinemarestspring.controller;

import com.daniilgrebenuk.cinemarestspring.dtos.OrderDto;
import com.daniilgrebenuk.cinemarestspring.exception.InvalidOrderException;
import com.daniilgrebenuk.cinemarestspring.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/reserve")
public class ReservationController {

  private final ReservationService reservationService;

  @PostMapping("/add")
  public ResponseEntity<?> reserveSeats(@RequestBody @Valid OrderDto order) {
    reservationService.reserveTickets(order);
    return ResponseEntity.ok().build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
    return ex.getBindingResult()
        .getAllErrors()
        .stream()
        .collect(Collectors.toMap(
            error -> ((FieldError) error).getField(),
            error -> Optional.ofNullable(error.getDefaultMessage()).orElse("No error specified!")
        ));
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(InvalidOrderException.class)
  public String handleValidationExceptions(InvalidOrderException ex) {
    return ex.getMessage();
  }
}
