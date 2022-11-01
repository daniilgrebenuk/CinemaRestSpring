package com.daniilgrebenuk.cinemarestspring.controller;

import com.daniilgrebenuk.cinemarestspring.dtos.ConfirmationDto;
import com.daniilgrebenuk.cinemarestspring.dtos.ReservationDto;
import com.daniilgrebenuk.cinemarestspring.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

  private final ReservationService reservationService;

  @PostMapping
  public ResponseEntity<ConfirmationDto> reserveSeats(@RequestBody @Valid ReservationDto reservation) {
    return ResponseEntity.ok(reservationService.reserveTickets(reservation));
  }

}
