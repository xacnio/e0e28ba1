package com.example.amdscs.repositories;

import com.example.amdscs.models.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    Flight findById(long id);
    Page<Flight> findAllByDepartureDateTimeBetweenAndDepartureAirportIdAndArrivalAirportId(LocalDateTime departureDateStart, LocalDateTime departureDateEnd, Long departureAirportId, Long arrivalAirportId, Pageable pageable);
}