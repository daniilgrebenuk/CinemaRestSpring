package com.daniilgrebenuk.cinemarestspring.util;

import com.daniilgrebenuk.cinemarestspring.dtos.HallDto;
import com.daniilgrebenuk.cinemarestspring.dtos.ScheduleDto;
import com.daniilgrebenuk.cinemarestspring.dtos.SeatDto;
import com.daniilgrebenuk.cinemarestspring.model.Hall;
import com.daniilgrebenuk.cinemarestspring.model.Schedule;
import com.daniilgrebenuk.cinemarestspring.model.Seat;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DtoConverter {

  public ScheduleDto scheduleDtoFromSchedule(Schedule schedule) {
    return ScheduleDto.builder()
        .idSchedule(schedule.getIdSchedule())
        .hallName(schedule.getHall().getName())
        .movieTitle(schedule.getMovie().getTitle())
        .time(schedule.getTime())
        .build();
  }

  public SeatDto seatDtoFromSeat(Seat seat) {
    return SeatDto.builder()
        .seatRow(seat.getSeatRow())
        .seatNumber(seat.getSeatNumber())
        .build();
  }

  public HallDto hallDtoFromHallAndSeats(Hall hall, List<Seat> seats) {
    return HallDto.builder()
        .hallName(hall.getName())
        .availableSeats(seats.stream().map(this::seatDtoFromSeat).toList())
        .build();
  }
}
