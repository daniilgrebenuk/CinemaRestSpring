package com.daniilgrebenuk.cinemarestspring.integration;

import com.daniilgrebenuk.cinemarestspring.dtos.HallDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ActiveProfiles(profiles = "tst")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HallControllerTest {

  @LocalServerPort
  int port;
  @Autowired
  TestRestTemplate restTemplate;

  String defaultUrl = "http://localhost:%d/api/halls/schedule/%d";

  @Test
  void findHallByScheduleId() {
    ResponseEntity<HallDto> response = restTemplate.getForEntity(
        String.format(defaultUrl, port, 3),
        HallDto.class
    );

    Optional<HallDto> hallDto = Optional.ofNullable(response.getBody());

    assertAll(
        () -> assertThat(response.getStatusCodeValue()).isEqualTo(200),
        () -> assertThat(hallDto.map(HallDto::hallName).orElse(null)).isEqualTo("Blue"),
        () -> assertThat(hallDto.map(HallDto::availableSeats).orElse(null)).hasSize(100)
    );
  }

  @Test
  void findHallWithIncorrectScheduleId() {
    ResponseEntity<String> response = restTemplate.getForEntity(
        String.format(defaultUrl, port, 9999),
        String.class
    );


    assertAll(
        () -> assertThat(response.getStatusCodeValue()).isEqualTo(404),
        () -> assertThat(response.getBody()).contains("Schedule with id: \\\"9999\\\" doesn't exist!")

    );
  }
}
