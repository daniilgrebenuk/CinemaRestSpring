package com.daniilgrebenuk.cinemarestspring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Data
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

  public Long getIdHall() {
    return idHall;
  }

  public List<Seat> getSeats() {
    return seats;
  }
}