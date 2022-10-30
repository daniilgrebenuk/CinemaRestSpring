package com.daniilgrebenuk.cinemarestspring.controller;

import com.daniilgrebenuk.cinemarestspring.dtos.ReservationDto;
import com.daniilgrebenuk.cinemarestspring.service.ReservationService;
import lombok.RequiredArgsConstructor;
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


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reserve")
public class ReservationController {

  private final ReservationService reservationService;

  @PostMapping("/add")
  public ResponseEntity<?> reserveSeats(@RequestBody @Valid ReservationDto reservation) {
    return ResponseEntity.ok(reservationService.reserveTickets(reservation));
  }

}
