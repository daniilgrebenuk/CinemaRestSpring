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
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TicketType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idTicketType;

  private String type;
  private Double price;

  public String getType() {
    return type;
  }

  public Double getPrice() {
    return price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    TicketType that = (TicketType) o;
    return idTicketType != null && Objects.equals(idTicketType, that.idTicketType);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
