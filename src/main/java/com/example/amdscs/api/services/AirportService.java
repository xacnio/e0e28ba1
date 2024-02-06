package com.example.amdscs.api.services;

import com.example.amdscs.models.Airport;
import com.example.amdscs.repositories.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportService {
    private final AirportRepository airportRepository;

    @Autowired
    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public Page<Airport> findAll(int page, int size, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("DESC") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return airportRepository.findAll(pageable);
    }

    public Airport getAirportById(Long id) {
        return airportRepository.findById(id).orElse(null);
    }

    public Airport createAirport(Airport airport) {
        airport.setId(null);
        airportRepository.save(airport);
        return airport;
    }

    public List<Airport> createAirports(List<Airport> airports) {
        airportRepository.saveAll(airports);
        return airports;
    }

    public Airport updateAirport(Airport airport) {
        if(airportRepository.existsById(airport.getId())) {
            return airportRepository.save(airport);
        }
        return null;
    }

    public boolean deleteAirport(Long id) {
        if(airportRepository.existsById(id)) {
            airportRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
