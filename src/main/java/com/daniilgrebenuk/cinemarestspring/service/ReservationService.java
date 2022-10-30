package com.daniilgrebenuk.cinemarestspring.service;

import com.daniilgrebenuk.cinemarestspring.dtos.ConfirmationDto;
import com.daniilgrebenuk.cinemarestspring.dtos.ReservationDto;

public interface ReservationService {

  ConfirmationDto reserveTickets(ReservationDto reservationDto);
}
