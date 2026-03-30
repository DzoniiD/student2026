package me.fit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer_order")
public class CustomerOrder {

    @Id
    @GeneratedValue
    public Long id;

    public String orderDate;
    public String status;

    @ManyToOne
    @JsonIgnore
    public Customer customer;

    @ManyToOne
    @JsonIgnore
    public Employee employee;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    public List<OrderItem> items;
}
