package com.daniilgrebenuk.cinemarestspring.dtos;

import com.daniilgrebenuk.cinemarestspring.util.NamingRegexConstants;
import com.daniilgrebenuk.cinemarestspring.util.TimePatternConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
    @Length(min = 3)
    @Pattern(regexp = NamingRegexConstants.CUSTOMER_NAME_REGEX)
    String customerName,

    @Length(min = 3)
    @Pattern(regexp = NamingRegexConstants.CUSTOMER_SURNAME_REGEX)
    String customerSurname,

    @JsonFormat(pattern = TimePatternConstants.LOCAL_DATE_TIME_PATTER)
    LocalDateTime dateAndTime,

    String movieTitle,

    String hallName,

    List<TicketDto> tickets
) {

}
