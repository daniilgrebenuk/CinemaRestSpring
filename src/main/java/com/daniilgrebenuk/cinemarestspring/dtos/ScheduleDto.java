package com.daniilgrebenuk.cinemarestspring.dtos;

import com.daniilgrebenuk.cinemarestspring.util.TimePatternConstants;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ScheduleDto(
    String movieTitle,
    @JsonFormat(pattern = TimePatternConstants.LOCAL_DATE_TIME_PATTER) LocalDateTime time
) {

}
