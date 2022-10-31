package com.daniilgrebenuk.cinemarestspring.dtos;

import java.util.List;

public record HallDto(
    String hallName,
    List<SeatDto> availableSeats
) {

}
