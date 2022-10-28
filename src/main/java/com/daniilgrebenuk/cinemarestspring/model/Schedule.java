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
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idSchedule;

  @ManyToOne
  @JoinColumn(name = "idHall")
  private Hall hall;

  @ManyToOne
  @JoinColumn(name = "idMovie")
  private Movie movie;

  private LocalDateTime time;

  public Long getIdSchedule() {
    return idSchedule;
  }
}
