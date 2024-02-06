package com.example.amdscs.repositories;

import com.example.amdscs.api.services.AirportService;
import com.example.amdscs.models.Airport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final AirportService airportService;

    @Autowired
    public DataLoader(AirportService airportService) {
        this.airportService = airportService;
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> cityNames = List.of(
                "Istanbul", "London", "New York", "Paris", "Tokyo",
                "Berlin", "Rome", "Sydney", "Mumbai", "Cairo",
                "Seoul", "Moscow", "Toronto", "Dubai", "Rio de Janeiro"
        );
        List<Airport> airports = new ArrayList<>();
        long i = 1L;
        for(var city : cityNames) {
            airports.add(new Airport(i++, city));
        }
        airportService.createAirports(airports);
    }
}