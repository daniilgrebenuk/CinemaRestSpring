package com.daniilgrebenuk.cinemarestspring.dtos;

import com.daniilgrebenuk.cinemarestspring.util.GlobalConstants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public record ReservationDto(
    @Size(min = 3)
    @Pattern(regexp = GlobalConstants.CUSTOMER_NAME_REGEX)
    @NotNull
    String customerName,

    @Size(min = 3)
    @Pattern(regexp = GlobalConstants.CUSTOMER_SURNAME_REGEX)
    @NotNull
    String customerSurname,

    @NotNull
    Long idSchedule,

    @NotNull
    List<TicketDto> tickets
) {

}
