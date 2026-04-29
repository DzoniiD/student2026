package me.fit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue
    public Long id;

    public String firstName;
    public String lastName;
    public String email;

    @OneToOne(cascade = CascadeType.ALL)
    public Address address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    public List<CustomerOrder> orders;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    public List<TimezoneInfo> timezones;
}