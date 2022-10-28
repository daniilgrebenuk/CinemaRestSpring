package com.daniilgrebenuk.cinemarestspring.service;


import com.daniilgrebenuk.cinemarestspring.dtos.ScheduleDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ScheduleService {

  List<ScheduleDto> findAllSchedulesByDayAndTimeFromAndTimeTo(LocalDate date, LocalTime from, LocalTime to);
}
