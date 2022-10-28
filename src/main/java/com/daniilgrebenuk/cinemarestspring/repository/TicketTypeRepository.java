package com.daniilgrebenuk.cinemarestspring.repository;

import com.daniilgrebenuk.cinemarestspring.model.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketTypeRepository extends JpaRepository<TicketType, Long> {

}
