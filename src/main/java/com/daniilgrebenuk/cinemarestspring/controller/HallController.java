package com.daniilgrebenuk.cinemarestspring.controller;

import com.daniilgrebenuk.cinemarestspring.dtos.HallDto;
import com.daniilgrebenuk.cinemarestspring.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hall")
@RequiredArgsConstructor
public class HallController {

  private final HallService hallService;

  @GetMapping("/schedule/{idSchedule}")
  public ResponseEntity<HallDto> findById(@PathVariable Long idSchedule) {
    return ResponseEntity.ok(hallService.getHallDtoByScheduleId(idSchedule));
  }
}
