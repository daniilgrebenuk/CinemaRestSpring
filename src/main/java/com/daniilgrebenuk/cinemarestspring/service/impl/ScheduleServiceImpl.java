package com.daniilgrebenuk.cinemarestspring.service.impl;

import com.daniilgrebenuk.cinemarestspring.dtos.ScheduleDto;
import com.daniilgrebenuk.cinemarestspring.repository.ScheduleRepository;
import com.daniilgrebenuk.cinemarestspring.service.ScheduleService;
import com.daniilgrebenuk.cinemarestspring.util.DtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

  private final ScheduleRepository scheduleRepository;
  private final DtoConverter dtoConverter;

  @Override
  public List<ScheduleDto> findAllSchedulesByDayAndTimeFromAndTimeTo(LocalDate date, LocalTime timeFrom, LocalTime timeTo) {
    return scheduleRepository
        .findAllScheduleBetweenTwoTimeStampOrderByMovieTitleAndTime(
            maxOfLocalDateTime(LocalDateTime.of(date, timeFrom), LocalDateTime.now()).plusMinutes(15),
            LocalDateTime.of(date, timeTo)
        )
        .stream()
        .map(dtoConverter::scheduleDtoFromSchedule)
        .collect(Collectors.toList());
  }

  private LocalDateTime maxOfLocalDateTime(LocalDateTime first, LocalDateTime second) {
    return first.compareTo(second) > 0 ? first : second;
  }
}
