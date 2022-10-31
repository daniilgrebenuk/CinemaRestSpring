package com.daniilgrebenuk.cinemarestspring.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TicketType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idTicketType;

  private String type;
  private Double price;

  public Long getIdTicketType() {
    return idTicketType;
  }

  public void setIdTicketType(Long idTicketType) {
    this.idTicketType = idTicketType;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TicketType that = (TicketType) o;
    return Objects.equals(idTicketType, that.idTicketType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idTicketType);
  }

  @Override
  public String toString() {
    return "TicketType{" +
        "idTicketType=" + idTicketType +
        ", type='" + type + '\'' +
        ", price=" + price +
        '}';
  }
}
