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
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Seat {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idSeat;

  @ManyToOne
  @JoinColumn(name = "idHall")
  private Hall hall;

  private Integer seatRow;
  private Integer seatNumber;

  public Long getIdSeat() {
    return idSeat;
  }

  public void setIdSeat(Long idSeat) {
    this.idSeat = idSeat;
  }

  public Hall getHall() {
    return hall;
  }

  public void setHall(Hall hall) {
    this.hall = hall;
  }

  public Integer getSeatRow() {
    return seatRow;
  }

  public void setSeatRow(Integer seatRow) {
    this.seatRow = seatRow;
  }

  public Integer getSeatNumber() {
    return seatNumber;
  }

  public void setSeatNumber(Integer seatNumber) {
    this.seatNumber = seatNumber;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Seat seat = (Seat) o;
    return Objects.equals(idSeat, seat.idSeat);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idSeat);
  }

  @Override
  public String toString() {
    return "Seat{" +
        "idSeat=" + idSeat +
        ", hall=" + hall +
        ", seatRow=" + seatRow +
        ", seatNumber=" + seatNumber +
        '}';
  }
}
