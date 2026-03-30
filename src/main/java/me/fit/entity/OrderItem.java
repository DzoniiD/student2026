package me.fit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue
    public Long id;

    public Integer quantity;

    @ManyToOne
    @JsonIgnore
    public CustomerOrder order;

    @ManyToOne
    public Product product;

}