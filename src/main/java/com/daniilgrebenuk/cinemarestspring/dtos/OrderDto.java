package com.daniilgrebenuk.cinemarestspring.dtos;

import com.daniilgrebenuk.cinemarestspring.util.NamingRegexConstants;
import com.daniilgrebenuk.cinemarestspring.util.TimePatternConstants;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
    @Size(min = 3)
    @Pattern(regexp = NamingRegexConstants.CUSTOMER_NAME_REGEX)
    @NotNull
    String customerName,

    @Size(min = 3)
    @Pattern(regexp = NamingRegexConstants.CUSTOMER_SURNAME_REGEX)
    @NotNull
    String customerSurname,

    @JsonFormat(pattern = TimePatternConstants.LOCAL_DATE_TIME_PATTER)
    @NotNull
    LocalDateTime dateAndTime,

    @NotNull
    String hallName,

    @NotNull
    List<TicketDto> tickets
) {

}
