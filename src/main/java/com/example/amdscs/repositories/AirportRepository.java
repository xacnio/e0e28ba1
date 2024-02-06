package com.example.amdscs.repositories;

import com.example.amdscs.models.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AirportRepository extends JpaRepository<Airport, Long> {
    List<Airport> findByCity(String city);
    Airport findById(long id);
}