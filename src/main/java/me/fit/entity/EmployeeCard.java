package me.fit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class EmployeeCard {

    @Id
    @GeneratedValue
    public Long id;

    public String cardNumber;
    public String issueDate;
}
