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
    return new ScheduleDto(
        schedule.getIdSchedule(),
        schedule.getHall().getName(),
        schedule.getMovie().getTitle(),
        schedule.getTime()
    );
  }

  public SeatDto seatDtoFromSeat(Seat seat) {
    return new SeatDto(
        seat.getSeatRow(),
        seat.getSeatNumber()
    );
  }

  public HallDto hallDtoFromHallAndSeats(Hall hall, List<Seat> seats) {
    return new HallDto(
        hall.getName(),
        seats.stream().map(this::seatDtoFromSeat).toList()
    );
  }
}
