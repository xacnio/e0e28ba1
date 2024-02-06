package com.example.amdscs.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "airports")
public class Airport {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String city;

    protected Airport() { }

    public Airport(String city) {
        this.city = city;
    }
    public Airport(Long id, String city) {
        this.id = id;
        this.city = city;
    }
}
