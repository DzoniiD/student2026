package me.fit.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Employee {

    @Id
    @GeneratedValue
    public Long id;

    public String firstName;
    public String lastName;
    public String position;

    @OneToOne(cascade = CascadeType.ALL)
    public EmployeeCard employeeCard;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<CustomerOrder> orders;
}