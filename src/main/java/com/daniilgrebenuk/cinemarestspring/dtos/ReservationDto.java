package com.daniilgrebenuk.cinemarestspring.dtos;

import com.daniilgrebenuk.cinemarestspring.util.GlobalConstants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public record ReservationDto(
    @Size(min = 3, message = "minimum length must be 3")
    @Pattern(regexp = GlobalConstants.CUSTOMER_NAME_REGEX, message = "should fit the pattern: \"" + GlobalConstants.CUSTOMER_NAME_REGEX + "\"")
    @NotNull(message = "The field must be present")
    String customerName,

    @Size(min = 3, message = "minimum length must be 3")
    @Pattern(regexp = GlobalConstants.CUSTOMER_SURNAME_REGEX, message = "should fit the pattern: \"" + GlobalConstants.CUSTOMER_SURNAME_REGEX + "\"")
    @NotNull(message = "The field must be present")
    String customerSurname,

    @NotNull(message = "The field must be present")
    Long idSchedule,

    @NotNull(message = "The field must be present")
    List<TicketDto> tickets
) {

}
