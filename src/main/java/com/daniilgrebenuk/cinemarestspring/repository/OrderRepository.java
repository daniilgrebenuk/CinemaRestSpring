package com.daniilgrebenuk.cinemarestspring.repository;

import com.daniilgrebenuk.cinemarestspring.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
