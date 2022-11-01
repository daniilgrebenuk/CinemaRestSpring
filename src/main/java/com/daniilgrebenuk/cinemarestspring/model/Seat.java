package com.daniilgrebenuk.cinemarestspring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
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

  public Integer getSeatRow() {
    return seatRow;
  }

  public Integer getSeatNumber() {
    return seatNumber;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Seat seat = (Seat) o;
    return idSeat != null && Objects.equals(idSeat, seat.idSeat);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
