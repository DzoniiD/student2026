package me.fit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Supplier {

    @Id
    @GeneratedValue
    public Long id;

    public String name;
    public String phone;

    @ManyToMany(mappedBy = "suppliers", fetch = FetchType.LAZY)
    @JsonIgnore
    public List<Product> products;
}
