package com.example.amdscs.scheduling;

import com.example.amdscs.api.services.AirportService;
import com.example.amdscs.api.services.FlightService;
import com.example.amdscs.models.Airport;
import com.example.amdscs.models.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class FlightsThirdPartyData {
    private FlightService flightService;

    @Autowired
    public FlightsThirdPartyData(FlightService flightService) {
        this.flightService = flightService;
    }

    //@Scheduled(fixedRate = 1000 * 60 * 60 * 24) // Executes every day (from start time)
    @Scheduled(cron = "0 0 1 * * *") // Executes every day but at 1:00 a.m. (cron format)
    public void performGetFlightsTask() {
        final String uri = "http://localhost:8080/mock/flights";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        List<Map<String, Object>> result = response.getBody();

        if(result != null) {
            List<Flight> flights = new ArrayList<>();
            result.forEach(data -> {
                Flight flight = new Flight();
                Airport depAirport = new Airport(Long.valueOf(data.get("departureAirportId").toString()), "");
                Airport arrAirport = new Airport(Long.valueOf(data.get("arrivalAirportId").toString()), "");
                flight.setDepartureAirport(depAirport);
                flight.setArrivalAirport(arrAirport);
                flight.setDepartureDateTime(LocalDateTime.parse(data.get("departureDateTime").toString()));
                flight.setArrivalDateTime(LocalDateTime.parse(data.get("arrivalDateTime").toString()));
                flight.setPrice(Double.parseDouble(data.get("price").toString()));
                flights.add(flight);
            });
            flightService.createFlights(flights);
        }
    }
}
