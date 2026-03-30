package me.fit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Address {

    @Id
    @GeneratedValue
    public Long id;

    public String city;
    public String street;
    public String number;
}