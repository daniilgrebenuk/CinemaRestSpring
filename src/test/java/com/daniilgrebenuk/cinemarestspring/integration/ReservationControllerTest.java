package com.daniilgrebenuk.cinemarestspring.integration;

import com.daniilgrebenuk.cinemarestspring.dtos.ConfirmationDto;
import com.daniilgrebenuk.cinemarestspring.dtos.ReservationDto;
import com.daniilgrebenuk.cinemarestspring.dtos.SeatDto;
import com.daniilgrebenuk.cinemarestspring.dtos.TicketDto;
import com.daniilgrebenuk.cinemarestspring.util.GlobalConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ActiveProfiles(profiles = "tst")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {

  @LocalServerPort
  int port;
  @Autowired
  TestRestTemplate restTemplate;

  @Test
  void reserveTicketsWithCorrectInput45MinutesBeforeFilm() {
    ReservationDto correctReservationDto = createCorrectReservationDto(3L);
    ResponseEntity<ConfirmationDto> response = restTemplate.postForEntity(
        String.format("http://localhost:%d/api/reserve/add", port),
        correctReservationDto,
        ConfirmationDto.class
    );

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(GlobalConstants.LOCAL_DATE_TIME_PATTER);

    // Reservation is valid for either 2 hours or until the end of the possibility to reserve
    String expectedExpireDate = LocalDateTime.now().plusMinutes(30).format(formatter);

    Optional<ConfirmationDto> confirmationDto = Optional.ofNullable(response.getBody());
    assertAll(
        () -> assertThat(response.getStatusCodeValue()).isEqualTo(200),
        () -> assertThat(confirmationDto.map(ConfirmationDto::totalSum).orElse(null)).isEqualTo(55.5),
        () -> assertThat(confirmationDto.map(ConfirmationDto::expireDate).map(d -> d.format(formatter)).orElse(null)).isEqualTo(expectedExpireDate)
    );
  }

  @Test
  void reserveTicketsWithCorrectInput165MinutesBeforeFilm() {
    ReservationDto correctReservationDto = createCorrectReservationDto(6L);
    ResponseEntity<ConfirmationDto> response = restTemplate.postForEntity(
        String.format("http://localhost:%d/api/reserve/add", port),
        correctReservationDto,
        ConfirmationDto.class
    );

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(GlobalConstants.LOCAL_DATE_TIME_PATTER);

    // Reservation is valid for either 2 hours or until the end of the possibility to reserve
    String expectedExpireDate = LocalDateTime.now().plusHours(GlobalConstants.EXPIRATION_TIME_IN_HOURS).format(formatter);

    Optional<ConfirmationDto> confirmationDto = Optional.ofNullable(response.getBody());
    assertAll(
        () -> assertThat(response.getStatusCodeValue()).isEqualTo(200),
        () -> assertThat(confirmationDto.map(ConfirmationDto::totalSum).orElse(null)).isEqualTo(55.5),
        () -> assertThat(confirmationDto.map(ConfirmationDto::expireDate).map(d -> d.format(formatter)).orElse(null)).isEqualTo(expectedExpireDate)
    );
  }

  @Test
  void reserveTicketsWithCorrectInput5MinutesBeforeFilm() {
    ReservationDto reservationDto = createCorrectReservationDto(2L);
    ResponseEntity<String> response = restTemplate.postForEntity(
        String.format("http://localhost:%d/api/reserve/add", port),
        reservationDto,
        String.class
    );

    assertAll(
        () -> assertThat(response.getStatusCodeValue()).isEqualTo(400),
        () -> assertThat(response.getBody()).contains("Tickets can only be purchased 15 minutes before the start!")
    );
  }

  @Test
  void reserveWithoutSeats() {
    ReservationDto reservationDto = new ReservationDto("Bob", "Bob", 3L, List.of());
    ResponseEntity<String> response = restTemplate.postForEntity(
        String.format("http://localhost:%d/api/reserve/add", port),
        reservationDto,
        String.class
    );


    assertAll(
        () -> assertThat(response.getStatusCodeValue()).isEqualTo(400),
        () -> assertThat(response.getBody()).contains("You must select at least one seat!")
    );
  }

  @Test
  void reserveWithIncorrectTicketType() {
    ReservationDto reservationDto = createReservationWithIncorrectTicketTypes();
    ResponseEntity<String> response = restTemplate.postForEntity(
        String.format("http://localhost:%d/api/reserve/add", port),
        reservationDto,
        String.class
    );

    assertAll(
        () -> assertThat(response.getStatusCodeValue()).isEqualTo(400),
        () -> assertThat(response.getBody())
            .contains("Invalid ticket types specified:")
            .contains("firstInvalidType")
            .contains("secondInvalidType")
            .contains("thirdInvalidType")
    );
  }

  @Test
  void reserveWithIncorrectScheduleId() {
    ReservationDto reservationDto = createCorrectReservationDto(-1L);
    ResponseEntity<String> response = restTemplate.postForEntity(
        String.format("http://localhost:%d/api/reserve/add", port),
        reservationDto,
        String.class
    );

    assertAll(
        () -> assertThat(response.getStatusCodeValue()).isEqualTo(400),
        () -> assertThat(response.getBody())
            .contains("There is no schedule suitable for this reservation!")
    );
  }

  @Test
  void reserveLeavingEmptySeatBetweenTwoReserved() {
    ReservationDto reservationDto = createReservationWithOneAvailableSeatBetweenTwoReserved();
    ResponseEntity<String> response = restTemplate.postForEntity(
        String.format("http://localhost:%d/api/reserve/add", port),
        reservationDto,
        String.class
    );

    assertAll(
        () -> assertThat(response.getStatusCodeValue()).isEqualTo(400),
        () -> assertThat(response.getBody())
            .contains("You cannot leave an available seat between two reserved seats!")
    );
  }

  @Test
  void reserveAlreadyReservedSeat() {
    ReservationDto correctReservationDto = createCorrectReservationDto(3L);
    restTemplate.postForEntity(
        String.format("http://localhost:%d/api/reserve/add", port),
        correctReservationDto,
        ConfirmationDto.class
    );

    ResponseEntity<String> response = restTemplate.postForEntity(
        String.format("http://localhost:%d/api/reserve/add", port),
        correctReservationDto,
        String.class
    );

    assertAll(
        () -> assertThat(response.getStatusCodeValue()).isEqualTo(400),
        () -> assertThat(response.getBody())
            .contains("Input seats are already reserved:")
            .contains("seatRow=1")
            .contains("seatNumber=1")
            .contains("seatNumber=2")
            .contains("seatNumber=3")
    );
  }

  @Test
  void reserveWithInvalidName() {
    ReservationDto correctReservationDto = new ReservationDto("", "", 3L, List.of());
    restTemplate.postForEntity(
        String.format("http://localhost:%d/api/reserve/add", port),
        correctReservationDto,
        ConfirmationDto.class
    );

    ResponseEntity<String> response = restTemplate.postForEntity(
        String.format("http://localhost:%d/api/reserve/add", port),
        correctReservationDto,
        String.class
    );
    System.out.println(response.getBody());
    assertAll(
        () -> assertThat(response.getStatusCodeValue()).isEqualTo(400),
        () -> assertThat(response.getBody())
            .contains("\"customerName\":\"should fit the pattern: \\\"[A-ZŻŹĆĄŚĘŁÓŃ][a-zżźćńółęąś]+\\\", minimum length must be 3\"")
            .contains("\"customerSurname\":\"minimum length must be 3, should fit the pattern: \\\"[A-ZŻŹĆĄŚĘŁÓŃ][a-zżźćńółęąś]+(-[A-ZŻŹĆĄŚĘŁÓŃ][a-zżźćńółęąś]+)?\\\"\"")
    );
  }

  private ReservationDto createReservationWithOneAvailableSeatBetweenTwoReserved() {
    return new ReservationDto(
        "Bob",
        "Bob",
        3L,
        List.of(
            new TicketDto(
                "adult",
                new SeatDto(
                    3,
                    1
                )
            ),
            new TicketDto(
                "child",
                new SeatDto(
                    3,
                    3
                )
            )
        )
    );
  }

  private ReservationDto createReservationWithIncorrectTicketTypes() {
    return new ReservationDto(
        "Bob",
        "Bob",
        3L,
        List.of(
            new TicketDto(
                "firstInvalidType",
                new SeatDto(
                    2,
                    1
                )
            ),
            new TicketDto(
                "secondInvalidType",
                new SeatDto(
                    2,
                    2
                )
            ),
            new TicketDto(
                "thirdInvalidType",
                new SeatDto(
                    2,
                    3
                )
            )
        )
    );
  }


  private ReservationDto createCorrectReservationDto(Long idSchedule) {
    return new ReservationDto(
        "Bob",
        "Bob",
        idSchedule,
        List.of(
            new TicketDto(
                "adult",
                new SeatDto(
                    1,
                    1
                )
            ),
            new TicketDto(
                "student",
                new SeatDto(
                    1,
                    2
                )
            ),
            new TicketDto(
                "child",
                new SeatDto(
                    1,
                    3
                )
            )
        )
    );
  }
}
