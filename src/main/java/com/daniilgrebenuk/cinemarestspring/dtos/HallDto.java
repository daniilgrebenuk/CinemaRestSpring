package com.daniilgrebenuk.cinemarestspring.dtos;

import lombok.Builder;

import java.util.List;

@Builder
public record HallDto(
    String hallName,
    List<SeatDto> availableSeats
) {
}


