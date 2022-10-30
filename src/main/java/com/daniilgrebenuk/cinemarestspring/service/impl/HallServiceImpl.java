package com.daniilgrebenuk.cinemarestspring.service.impl;

import com.daniilgrebenuk.cinemarestspring.dtos.HallDto;
import com.daniilgrebenuk.cinemarestspring.exception.DataNotFoundException;
import com.daniilgrebenuk.cinemarestspring.repository.ScheduleRepository;
import com.daniilgrebenuk.cinemarestspring.repository.SeatRepository;
import com.daniilgrebenuk.cinemarestspring.service.HallService;
import com.daniilgrebenuk.cinemarestspring.util.DtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HallServiceImpl implements HallService {

  private final ScheduleRepository scheduleRepository;
  private final SeatRepository seatRepository;
  private final DtoConverter dtoConverter;

  @Override
  public HallDto getHallDtoByScheduleId(Long idSchedule) {

    return dtoConverter.hallDtoFromHallAndSeats(
        scheduleRepository
            .findById(idSchedule)
            .orElseThrow(() -> new DataNotFoundException("Schedule with id: \"" + idSchedule + "\" doesn't exist!"))
            .getHall(),
        seatRepository.findAllAvailableSeatsByIdSchedule(idSchedule)
    );
  }
}
