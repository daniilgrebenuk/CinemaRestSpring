package com.daniilgrebenuk.cinemarestspring.service;

import com.daniilgrebenuk.cinemarestspring.dtos.OrderDto;

public interface ReservationService {

  void reserveTickets(OrderDto orderDto);
}
