package com.daniilgrebenuk.cinemarestspring.integration;


import com.daniilgrebenuk.cinemarestspring.dtos.ScheduleDto;
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
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ActiveProfiles(profiles = "tst")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScheduleControllerTest {

  @LocalServerPort
  int port;
  @Autowired
  TestRestTemplate restTemplate;

  @Test
  void findAllSchedulesOfCurrentYear() {
    LocalDateTime from = LocalDateTime.of(LocalDateTime.now().getYear(), 1, 1, 0, 0);
    LocalDateTime to = LocalDateTime.of(LocalDateTime.now().getYear(), 12, 31, 23, 59);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(GlobalConstants.LOCAL_DATE_TIME_PATTER);

    ResponseEntity<ScheduleDto[]> response = restTemplate.getForEntity(
        String.format("http://localhost:%d/api/schedules/all/range/?timeFrom=%s&timeTo=%s", port, from.format(formatter), to.format(formatter)),
        ScheduleDto[].class
    );
    String bodyString = Optional.ofNullable(response.getBody()).map(Arrays::toString).orElse(null);

    assertAll(
        () -> assertThat(response.getStatusCodeValue()).isEqualTo(200),
        () -> assertThat(response.getBody()).hasSize(10),
        () -> assertThat(bodyString)
            .contains("BLACK ADAM")
            .contains("ONE PIECE")
            .contains("NEL I TAJEMNICA KUROKOTA")
            .contains("ANIA")
            .contains("UŚMIECHNIJ SIĘ")
            .contains("Red 3D")
            .contains("Green")
            .contains("Blue")
    );
  }

  @Test
  void findAllSchedulesFromLastYearTo15MinutesAgo() {
    LocalDateTime from = LocalDateTime.now().minusYears(1);
    LocalDateTime to = LocalDateTime.now().minusMinutes(15);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(GlobalConstants.LOCAL_DATE_TIME_PATTER);

    ResponseEntity<ScheduleDto[]> response = restTemplate.getForEntity(
        String.format("http://localhost:%d/api/schedules/all/range/?timeFrom=%s&timeTo=%s", port, from.format(formatter), to.format(formatter)),
        ScheduleDto[].class
    );

    assertAll(
        () -> assertThat(response.getStatusCodeValue()).isEqualTo(200),
        () -> assertThat(response.getBody()).hasSize(0)
    );
  }
}
