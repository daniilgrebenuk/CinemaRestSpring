package com.daniilgrebenuk.cinemarestspring.util;

import com.daniilgrebenuk.cinemarestspring.dtos.ScheduleDto;
import com.daniilgrebenuk.cinemarestspring.dtos.SeatDto;
import com.daniilgrebenuk.cinemarestspring.model.Schedule;
import com.daniilgrebenuk.cinemarestspring.model.Seat;
import org.springframework.stereotype.Component;

@Component
public class DtoConverter {

  public ScheduleDto scheduleDtoFromSchedule(Schedule schedule) {
    return new ScheduleDto(
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
}
