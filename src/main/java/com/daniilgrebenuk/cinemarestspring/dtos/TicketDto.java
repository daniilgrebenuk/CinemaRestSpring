package com.daniilgrebenuk.cinemarestspring.dtos;

public record TicketDto(
    String ticketType,
    SeatDto seat
) {}
