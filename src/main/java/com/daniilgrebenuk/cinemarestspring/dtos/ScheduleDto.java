package com.daniilgrebenuk.cinemarestspring.dtos;

import com.daniilgrebenuk.cinemarestspring.util.GlobalConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ScheduleDto(
    Long idSchedule,
    String hallName,
    String movieTitle,
    @JsonFormat(pattern = GlobalConstants.LOCAL_DATE_TIME_PATTERN)
    LocalDateTime time
) {

}
