package com.daniilgrebenuk.cinemarestspring.dtos;

import lombok.Builder;

@Builder
public record SeatDto(
    Integer seatRow,
    Integer seatNumber
) {

}
