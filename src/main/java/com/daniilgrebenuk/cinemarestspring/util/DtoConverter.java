package com.daniilgrebenuk.cinemarestspring.util;

import com.daniilgrebenuk.cinemarestspring.dtos.ScheduleDto;
import com.daniilgrebenuk.cinemarestspring.model.Schedule;
import org.springframework.stereotype.Component;

@Component
public class DtoConverter {
  public ScheduleDto scheduleDtoFromSchedule(Schedule schedule) {
    return new ScheduleDto(
        schedule.getMovie().getTitle(),
        schedule.getTime()
    );
  }
}
