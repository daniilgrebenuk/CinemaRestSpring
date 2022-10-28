package com.daniilgrebenuk.cinemarestspring.controller;

import com.daniilgrebenuk.cinemarestspring.service.ScheduleService;
import com.daniilgrebenuk.cinemarestspring.util.TimePatternConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

  private final ScheduleService scheduleService;

  @GetMapping
  public ResponseEntity<?> getSchedulesBetweenTwoDate(@Param("date") String date, @Param("timeFrom") String timeFrom, @Param("timeTo") String timeTo) {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(TimePatternConstants.LOCAL_DATE_PATTER);
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TimePatternConstants.LOCAL_TIME_PATTER);

    return ResponseEntity.ok(
        scheduleService.findAllSchedulesByDayAndTimeFromAndTimeTo(
            LocalDate.parse(date, dateFormatter),
            LocalTime.parse(timeFrom, timeFormatter),
            LocalTime.parse(timeTo, timeFormatter)
        )
    );
  }
}
