package com.daniilgrebenuk.cinemarestspring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idTicket;

  @OneToOne
  @JoinColumn(name = "idSeat")
  private Seat seat;

  @ManyToOne
  @JoinColumn(name = "idSchedule")
  private Schedule schedule;

  @ManyToOne
  @JoinColumn(name = "idTicketType")
  private TicketType ticketType;

  private String customerName;

  private String customerSurname;
}
