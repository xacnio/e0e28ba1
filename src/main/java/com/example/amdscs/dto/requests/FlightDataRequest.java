package com.example.amdscs.dto.requests;

import com.example.amdscs.models.Airport;
import com.example.amdscs.models.Flight;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightDataRequest {
    public Long departureAirportID;
    public Long arrivalAirportID;
    public LocalDateTime departureDateTime;
    public LocalDateTime arrivalDateTime;
    public double price;

    public Flight toFlight(Airport arrivalAirport, Airport departureAirport) {
        Flight flight = new Flight();
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureAirport(departureAirport);
        flight.setPrice(this.price);
        flight.setDepartureDateTime(this.departureDateTime);
        flight.setArrivalDateTime(this.arrivalDateTime);
        return flight;
    }
}
