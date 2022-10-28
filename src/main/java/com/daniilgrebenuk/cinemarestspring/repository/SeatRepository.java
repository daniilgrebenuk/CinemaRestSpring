package com.daniilgrebenuk.cinemarestspring.repository;

import com.daniilgrebenuk.cinemarestspring.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

  @Query(value = """
    SELECT seat.* FROM Seat seat
                JOIN Hall h ON seat.ID_HALL = h.ID_HALL
                JOIN Schedule s ON s.ID_HALL = h.ID_HALL
    WHERE s.ID_SCHEDULE = ?1
    EXCEPT
    SELECT seat.* FROM Seat seat
                JOIN Ticket t ON t.ID_SEAT = seat.ID_SEAT
                JOIN Schedule s ON t.ID_SCHEDULE = s.ID_SCHEDULE
    WHERE s.ID_SCHEDULE = ?1
""", nativeQuery = true)
  List<Seat> findAllAvailableSeatsByIdSchedule(Long scheduleId);

  List<Seat> findAllByHallName(String hallName);
}
