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
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
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

  public Hall getHall() {
    return hall;
  }

  public LocalDateTime getTime() {
    return time;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Schedule schedule = (Schedule) o;
    return idSchedule != null && Objects.equals(idSchedule, schedule.idSchedule);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
