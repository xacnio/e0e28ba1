package com.example.amdscs.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/mock")
public class MockController {
    private static final Random random = new Random();

    @GetMapping("/flights")
    public List<Map<String, ?>> ThirdPartyMockFlightsData() {
        var currentDateTime = LocalDateTime.now();
        List<Map<String, ?>> flights = new ArrayList<>();
        for(int i = 0; i < 64; i++) {
            var airportIds = getRandomAirports();
            var data = new HashMap<String, Object>();
            currentDateTime = currentDateTime.plusMinutes(30);
            data.put("departureAirportId", airportIds[0]);
            data.put("arrivalAirportId", airportIds[1]);
            data.put("departureDateTime", currentDateTime);
            data.put("arrivalDateTime", currentDateTime.plusMinutes(getRandomNumber(90,480)));
            data.put("price", getRandomNumber(100,1000));
            flights.add(data);
        }
        return flights;
    }

    private static int getRandomNumber(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
    public static int[] getRandomAirports() {
        Set<Integer> generatedNumbers = new HashSet<>();
        int randomDepAirport = getRandomNumber(1, 15);
        generatedNumbers.add(randomDepAirport);

        int randomArrAirport;
        do {
            randomArrAirport = getRandomNumber(1, 15);
        } while (generatedNumbers.contains(randomArrAirport));
        return new int[]{randomDepAirport, randomArrAirport};
    }
}
