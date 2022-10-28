package com.daniilgrebenuk.cinemarestspring.config;

import com.daniilgrebenuk.cinemarestspring.model.Hall;
import com.daniilgrebenuk.cinemarestspring.model.Movie;
import com.daniilgrebenuk.cinemarestspring.model.Schedule;
import com.daniilgrebenuk.cinemarestspring.model.Seat;
import com.daniilgrebenuk.cinemarestspring.model.TicketType;
import com.daniilgrebenuk.cinemarestspring.repository.HallRepository;
import com.daniilgrebenuk.cinemarestspring.repository.MovieRepository;
import com.daniilgrebenuk.cinemarestspring.repository.ScheduleRepository;
import com.daniilgrebenuk.cinemarestspring.repository.SeatRepository;
import com.daniilgrebenuk.cinemarestspring.repository.TicketTypeRepository;
import lombok.RequiredArgsConstructor;
import org.h2.tools.Server;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
@RequiredArgsConstructor
public class H2Config {

  private final HallRepository hallRepository;
  private final MovieRepository movieRepository;
  private final ScheduleRepository scheduleRepository;
  private final SeatRepository seatRepository;
  private final TicketTypeRepository ticketTypeRepository;


  @Profile("dev")
  @Bean(initMethod = "start", destroyMethod = "stop")
  public Server h2Server() throws SQLException {
    return Server.createTcpServer("-tcp");
  }

  @Bean
  public CommandLineRunner loadData() {
    return args -> {
      initMovies();
      initHalls();
      initSeats();
      initSchedules();
      initTicketType();
    };
  }

  private void initTicketType() {
    ticketTypeRepository.saveAll(List.of(
        new TicketType(null, "adult", 25.0),
        new TicketType(null, "student", 18.0),
        new TicketType(null, "child", 12.5)
    ));
  }

  private void initMovies() {
    movieRepository.saveAll(List.of(
        new Movie(null, "BLACK ADAM"),
        new Movie(null, "ONE PIECE"),
        new Movie(null, "UŚMIECHNIJ SIĘ")
    ));
  }

  private void initHalls() {
    hallRepository.saveAll(List.of(
        new Hall(null, "Red 3D"),
        new Hall(null, "Green"),
        new Hall(null, "Blue")
    ));
  }

  private void initSeats() {
    hallRepository.findAll().forEach(this::createSeatsForHall);
  }

  private void initSchedules() {
    LocalDateTime localDateTime = LocalDateTime.now().minusMinutes(150);
    List<Movie> movies = movieRepository.findAll();
    List<Hall> halls = hallRepository.findAll();
    int moviesCounter = 0;

    for (Hall hall : halls) {
      for (int i = 0; i < 2; i++) {
        if (moviesCounter == movies.size()) {
          moviesCounter = 0;
        }
        createScheduleWithHallMovieAndTime(
            hall,
            movies.get(moviesCounter++),
            localDateTime
        );
        localDateTime = localDateTime.plusMinutes(300);
      }
    }
  }

  private void createScheduleWithHallMovieAndTime(Hall hall, Movie movie, LocalDateTime localDateTime) {
    scheduleRepository.save(
        new Schedule(null, hall, movie, localDateTime)
    );
  }

  private void createSeatsForHall(Hall hall) {
    seatRepository.saveAll(
        IntStream.range(1, 11)
            .boxed()
            .flatMap(row -> IntStream.range(1, 11).mapToObj(column -> new Seat(null, hall, row, column)))
            .collect(Collectors.toList())
    );
  }
}
