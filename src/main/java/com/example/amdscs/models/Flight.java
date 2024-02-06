package com.example.amdscs.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "departure_airport_id")
    public Airport departureAirport;

    @ManyToOne
    @JoinColumn(name = "arrival_airport_id")
    public Airport arrivalAirport;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private double price;

    @Override
    public String toString() {
        return String.format("id=%d dep=%d arr=%d depDT=%s arrDT=%s price=%f", id, departureAirport.getId(), arrivalAirport.getId(), departureDateTime, arrivalDateTime, price);
    }
}
