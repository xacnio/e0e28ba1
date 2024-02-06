package com.example.amdscs.api.services;

import com.example.amdscs.dto.responses.SearchRes;
import com.example.amdscs.exceptions.ApiError;
import com.example.amdscs.models.Flight;
import com.example.amdscs.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightService {
    private final FlightRepository flightRepository;

    @Autowired
    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public SearchRes search(LocalDate departureDate, LocalDate arrivalDate, Long departureAirportId, Long arrivalAirportId, String sortBy, String sortOrder) {
        SearchRes result = new SearchRes();
        Sort sort = sortOrder.equalsIgnoreCase("DESC") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, sort);
        if(departureDate == null || departureAirportId == null || arrivalAirportId == null) {
            throw new ApiError(HttpStatus.BAD_REQUEST, "bad request");
        }
        LocalDateTime depStartDate = departureDate.atTime(0, 0, 0);
        LocalDateTime depEndDate = departureDate.atTime(23, 59, 59);
        result.departureFlights = flightRepository.findAllByDepartureDateTimeBetweenAndDepartureAirportIdAndArrivalAirportId(depStartDate, depEndDate, departureAirportId, arrivalAirportId, pageable).getContent();
        if(arrivalDate != null) {
            LocalDateTime arrStartDate = arrivalDate.atTime(0, 0, 0);
            LocalDateTime arrEndDate = arrivalDate.atTime(23, 59, 59);
            result.arrivalFlights = flightRepository.findAllByDepartureDateTimeBetweenAndDepartureAirportIdAndArrivalAirportId(arrStartDate, arrEndDate, arrivalAirportId, departureAirportId, pageable).getContent();
        }
        return result;
    }

    public Page<Flight> findAll(int page, int size, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("DESC") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return flightRepository.findAll(pageable);
    }

    public Flight getFlightById(Long id) {
        return flightRepository.findById(id).orElse(null);
    }

    public Flight createFlight(Flight flight) {
        flight.setId(null);
        flightRepository.save(flight);
        return flight;
    }

    public List<Flight> createFlights(List<Flight> flights) {
        flightRepository.saveAll(flights);
        return flights;
    }

    public Flight updateFlight(Flight flight) {
        if(flightRepository.existsById(flight.getId())) {
            return flightRepository.save(flight);
        }
        return null;
    }

    public boolean deleteFlight(Long id) {
        if(flightRepository.existsById(id)) {
            flightRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
