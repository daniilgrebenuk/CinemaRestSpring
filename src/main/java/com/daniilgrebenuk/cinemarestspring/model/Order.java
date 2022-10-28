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
import javax.persistence.Table;

@Data
@Entity
@Table(name = "Orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idOrder;

  @ManyToOne
  @JoinColumn(name = "idSchedule")
  private Schedule schedule;

  private String customerName;
  private String customerSurname;

  public Long getIdOrder() {
    return idOrder;
  }

  public void setIdOrder(Long idOrder) {
    this.idOrder = idOrder;
  }

  public Schedule getSchedule() {
    return schedule;
  }

  public void setSchedule(Schedule schedule) {
    this.schedule = schedule;
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
}
