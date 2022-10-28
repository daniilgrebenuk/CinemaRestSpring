package com.daniilgrebenuk.cinemarestspring.repository;

import com.daniilgrebenuk.cinemarestspring.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

  @Query(value = """
      SELECT s FROM Schedule s
               JOIN Movie m on s.movie = m
      WHERE s.time BETWEEN ?1 AND ?2
      ORDER BY m.title, s.time
      """)
  List<Schedule> findAllScheduleBetweenTwoTimeStampOrderByMovieTitleAndTime(LocalDateTime from, LocalDateTime to);

  @Query(value = """
      SELECT schedule FROM Schedule schedule
                      JOIN Movie m ON schedule.movie = m
                      JOIN Hall h ON schedule.hall = h
      WHERE m.title = ?1 AND h.name = ?2 AND schedule.time = ?3
      """)
  Optional<Schedule> findScheduleByMovie_TitleAndHall_NameAndTime(String movieTitle, String hallName, LocalDateTime time);
}
