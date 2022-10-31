package com.daniilgrebenuk.cinemarestspring.service;


import com.daniilgrebenuk.cinemarestspring.dtos.ScheduleDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleService {

  List<ScheduleDto> findAllSchedulesByDayAndTimeFromAndTimeTo(LocalDateTime timeFrom, LocalDateTime timeTo);
}
