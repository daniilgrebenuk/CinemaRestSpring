package com.daniilgrebenuk.cinemarestspring.dtos;

import com.daniilgrebenuk.cinemarestspring.util.GlobalConstants;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ScheduleDto(
    Long idSchedule,
    String hallName,
    String movieTitle,
    @JsonFormat(pattern = GlobalConstants.LOCAL_DATE_TIME_PATTER) LocalDateTime time
) {

}
