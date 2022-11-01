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
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idReservation;

  @ManyToOne
  @JoinColumn(name = "idSchedule")
  private Schedule schedule;

  private String customerName;
  private String customerSurname;

  private LocalDateTime expireDate;

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

  public Schedule getSchedule() {
    return schedule;
  }

  public void setSchedule(Schedule schedule) {
    this.schedule = schedule;
  }

  public Long getIdReservation() {
    return idReservation;
  }

  public void setIdReservation(Long idReservation) {
    this.idReservation = idReservation;
  }

  public LocalDateTime getExpireDate() {
    return expireDate;
  }

  public void setExpireDate(LocalDateTime expireDate) {
    this.expireDate = expireDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Reservation that = (Reservation) o;
    return idReservation != null && Objects.equals(idReservation, that.idReservation);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
