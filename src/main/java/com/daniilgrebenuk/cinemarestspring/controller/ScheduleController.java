package com.daniilgrebenuk.cinemarestspring.controller;

import com.daniilgrebenuk.cinemarestspring.dtos.ScheduleDto;
import com.daniilgrebenuk.cinemarestspring.service.ScheduleService;
import com.daniilgrebenuk.cinemarestspring.util.GlobalConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

  private final ScheduleService scheduleService;

  @GetMapping("/all/range")
  public ResponseEntity<List<ScheduleDto>> getSchedulesBetweenTwoDate(
      @Param("timeFrom") String timeFrom,
      @Param("timeTo") String timeTo) {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(GlobalConstants.LOCAL_DATE_TIME_PATTER);

    return ResponseEntity.ok(
        scheduleService.findAllSchedulesByDayAndTimeFromAndTimeTo(
            LocalDateTime.parse(timeFrom, dateFormatter),
            LocalDateTime.parse(timeTo, dateFormatter)
        )
    );
  }
}
