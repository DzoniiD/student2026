package me.fit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


import jakarta.persistence.*;

@Entity
public class TimezoneInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String timeZone;
    public String currentLocalTime;

    @ManyToOne
    public Customer customer;
}
