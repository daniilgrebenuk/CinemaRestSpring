package com.daniilgrebenuk.cinemarestspring.repository;

import com.daniilgrebenuk.cinemarestspring.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {

}
