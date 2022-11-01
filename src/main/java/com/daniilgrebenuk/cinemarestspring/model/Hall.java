package com.daniilgrebenuk.cinemarestspring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Hall {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idHall;

  private String name;

  @ToString.Exclude
  @JsonIgnore
  @OneToMany(mappedBy = "hall")
  List<Seat> seats;


  public List<Seat> getSeats() {
    return seats;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Hall hall = (Hall) o;
    return idHall != null && Objects.equals(idHall, hall.idHall);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}