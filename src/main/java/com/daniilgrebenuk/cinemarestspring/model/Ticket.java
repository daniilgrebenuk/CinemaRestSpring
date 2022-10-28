package com.daniilgrebenuk.cinemarestspring.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idTicket;

  @ManyToOne
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

  public Long getIdTicket() {
    return idTicket;
  }

  public void setIdTicket(Long idTicket) {
    this.idTicket = idTicket;
  }

  public Seat getSeat() {
    return seat;
  }

  public void setSeat(Seat seat) {
    this.seat = seat;
  }

  public Schedule getSchedule() {
    return schedule;
  }

  public void setSchedule(Schedule schedule) {
    this.schedule = schedule;
  }

  public TicketType getTicketType() {
    return ticketType;
  }

  public void setTicketType(TicketType ticketType) {
    this.ticketType = ticketType;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getCustomerSurname() {
    return customerSurname;
  }

  public void setCustomerSurname(String customerSurname) {
    this.customerSurname = customerSurname;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Ticket ticket = (Ticket) o;
    return Objects.equals(idTicket, ticket.idTicket);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idTicket);
  }

  @Override
  public String toString() {
    return "Ticket{" +
        "idTicket=" + idTicket +
        ", seat=" + seat +
        ", schedule=" + schedule +
        ", ticketType=" + ticketType +
        ", customerName='" + customerName + '\'' +
        ", customerSurname='" + customerSurname + '\'' +
        '}';
  }
}
