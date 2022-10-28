package com.daniilgrebenuk.cinemarestspring.dtos;

import com.daniilgrebenuk.cinemarestspring.util.TimePattern;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ScheduleDto (
    String movieTitle,
    @JsonFormat(pattern = TimePattern.LOCAL_DATE_TIME_PATTER) LocalDateTime time
){}
