package com.example.amdscs.api.controllers;

import com.example.amdscs.api.abstracts.FlightApi;
import com.example.amdscs.api.services.AirportService;
import com.example.amdscs.dto.requests.FlightDataRequest;
import com.example.amdscs.dto.responses.DeleteRes;
import com.example.amdscs.dto.responses.PageRes;
import com.example.amdscs.dto.responses.SearchRes;
import com.example.amdscs.models.Airport;
import com.example.amdscs.models.Flight;
import com.example.amdscs.exceptions.ApiError;
import com.example.amdscs.exceptions.ApiErrors;
import com.example.amdscs.api.services.FlightService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/flights")
public class FlightController implements FlightApi {
    private final FlightService flightService;
    private final AirportService airportService;
    public static final List<String> ALLOWED_SORT_BYS = List.of("departureDateTime", "arrivalDateTime", "price");

    @Autowired
    public FlightController(FlightService flightService, AirportService airportService) {
        this.flightService = flightService;
        this.airportService = airportService;
    }

    public PageRes<Flight> getFlights(int page, int limit, String sortBy, String sortOrder) {
        if(!ALLOWED_SORT_BYS.contains(sortBy)) {
            throw new ApiError(HttpStatus.BAD_REQUEST, "invalid sort by option");
        }
        Page<Flight> flights = flightService.findAll(page, limit, sortBy, sortOrder);

        PageRes<Flight> response = new PageRes<>();
        response.data = flights.getContent();
        response.hasPrevious = flights.hasPrevious();
        response.hasNext = flights.hasNext();
        response.totalElements = flights.getTotalElements();
        response.totalPages = flights.getTotalPages();
        return response;
    }

    public SearchRes searchFlights(LocalDate departureDate, LocalDate arrivalDate, Long departureAirportId, Long arrivalAirportId, String sortBy, String sortOrder) {
        if(!ALLOWED_SORT_BYS.contains(sortBy)) {
            throw new ApiError(HttpStatus.BAD_REQUEST, "invalid sort by option");
        }
        return flightService.search(departureDate, arrivalDate, departureAirportId, arrivalAirportId, sortBy, sortOrder);
    }

    public Flight getFlight(Long id) {
        Flight flight = flightService.getFlightById(id);
        if(flight == null) {
            throw new ApiError(ApiErrors.FLIGHT_NOT_FOUND);
        }
        return flight;
    }

    public Flight createFlight(FlightDataRequest req) {
        var flight = this.getFlightFromReq(req);
        return flightService.createFlight(flight);
    }

    public Flight updateFlight(Long id, FlightDataRequest req) {
        var flight = this.getFlightFromReq(req);
        flight.setId(id);
        return flightService.updateFlight(flight);
    }

    private Flight getFlightFromReq(FlightDataRequest req) {
        if(req.departureAirportID == null || req.arrivalAirportID == null) {
            throw new ApiError(ApiErrors.BAD_REQUEST);
        }
        Airport depAirport = airportService.getAirportById(req.departureAirportID);
        Airport arrAirport = airportService.getAirportById(req.arrivalAirportID);
        if(depAirport == null || arrAirport == null) {
            throw new ApiError(ApiErrors.AIRPORT_NOT_FOUND);
        }
        if(Objects.equals(req.departureAirportID, req.arrivalAirportID)) {
            throw new ApiError(ApiErrors.ARR_DEP_CONFLICT);
        }
        return req.toFlight(arrAirport, depAirport);
    }

    public Object deleteFlight(Long id) {
        var result = flightService.deleteFlight(id);
        if(!result) {
            throw new ApiError(ApiErrors.FLIGHT_NOT_FOUND);
        }
        return DeleteRes.of(true);
    }
}
