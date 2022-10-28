package com.daniilgrebenuk.cinemarestspring.repository;

import com.daniilgrebenuk.cinemarestspring.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

  @Query(value = """
      SELECT s FROM Schedule s
               JOIN Movie m on s.movie = m
      WHERE s.time BETWEEN ?1 AND ?2
      ORDER BY m.title, s.time
      """)
  List<Schedule> findAllScheduleBetweenTwoTimeStampOrderByMovieTitleAndTime(LocalDateTime from, LocalDateTime to);
}
