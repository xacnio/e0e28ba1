package com.example.amdscs.api.controllers;

import com.example.amdscs.api.abstracts.AirportApi;
import com.example.amdscs.dto.requests.AirportDataRequest;
import com.example.amdscs.dto.responses.DeleteRes;
import com.example.amdscs.dto.responses.PageRes;
import com.example.amdscs.models.Airport;
import com.example.amdscs.exceptions.ApiError;
import com.example.amdscs.exceptions.ApiErrors;
import com.example.amdscs.api.services.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/airports")
public class AirportController implements AirportApi {
    private final AirportService airportService;

    @Autowired
    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }


    public PageRes<Airport> getAirports(int page, int limit, String sortBy, String sortOrder) {
        Page<Airport> airports = airportService.findAll(page, limit, sortBy, sortOrder);

        PageRes<Airport> response = new PageRes<>();
        response.data = airports.getContent();
        response.hasPrevious = airports.hasPrevious();
        response.hasNext = airports.hasNext();
        response.totalElements = airports.getTotalElements();
        response.totalPages = airports.getTotalPages();
        return response;
    }

    public Airport getAirport(Long id) {
        Airport airport = airportService.getAirportById(id);
        if(airport == null) {
            throw new ApiError(ApiErrors.AIRPORT_NOT_FOUND);
        }
        return airport;
    }

    public Airport createAirport(AirportDataRequest req) {
        Airport cAirport = new Airport(req.city);
        return airportService.createAirport(cAirport);
    }

    public Airport updateAirport(Long id, AirportDataRequest req) {
        Airport cAirport = new Airport(id, req.city);
        return airportService.updateAirport(cAirport);
    }

    public Object deleteAirport(Long id) {
        var result = airportService.deleteAirport(id);
        if(!result) {
            throw new ApiError(ApiErrors.AIRPORT_NOT_FOUND);
        }
        return DeleteRes.of(true);
    }
}
