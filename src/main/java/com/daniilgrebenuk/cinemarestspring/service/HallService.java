package com.daniilgrebenuk.cinemarestspring.service;


import com.daniilgrebenuk.cinemarestspring.dtos.HallDto;

public interface HallService {
  HallDto getHallDtoByScheduleId(Long scheduleId);
}
