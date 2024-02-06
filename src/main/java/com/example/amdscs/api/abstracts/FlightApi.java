package com.example.amdscs.api.abstracts;

import com.example.amdscs.api.controllers.FlightController;
import com.example.amdscs.dto.requests.FlightDataRequest;
import com.example.amdscs.dto.responses.DeleteRes;
import com.example.amdscs.dto.responses.ErrorRes;
import com.example.amdscs.dto.responses.PageRes;
import com.example.amdscs.dto.responses.SearchRes;
import com.example.amdscs.models.Flight;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Flights", description = "Flight API")
public interface FlightApi {
    @GetMapping
    @Operation(summary= "Get a paginated list of flights")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful retrieval of flight data (if no elements, return an empty array)", useReturnTypeSchema = true)
    })
    @SecurityRequirement(name = "Authorization")
    PageRes<Flight> getFlights(
            @Parameter(description = "Page") @RequestParam(required = false, defaultValue = "0") int page,
            @Parameter(description = "Limit") @RequestParam(required = false, defaultValue = "5") int limit,
            @Parameter(description = "Sort by (default: departureDateTime)", schema = @Schema(allowableValues = {"departureDateTime", "arrivalDateTime", "price"}))
            @RequestParam(required = false, defaultValue = "departureDateTime") String sortBy,
            @Parameter(description = "Sort order (default: asc)", schema = @Schema(allowableValues = {"asc", "desc"}))
            @RequestParam(required = false, defaultValue = "asc") String sortOrder
    );

    @GetMapping("/{id}")
    @Operation(summary= "Get flight by ID", description= "Flight must exist")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful retrieval of flight data", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "Flight not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorRes.class))
            })
    })
    @SecurityRequirement(name = "Authorization")
    Flight getFlight(@PathVariable Long id);

    @PostMapping
    @Operation(summary= "Create a new flight")
    @SecurityRequirement(name = "Authorization")
    @ResponseStatus(HttpStatus.CREATED)
    Flight createFlight(@RequestBody FlightDataRequest req);

    @PutMapping("/{id}")
    @Operation(summary= "Update existing flight")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful updated of flight data", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "Flight not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorRes.class))
            })
    })
    @SecurityRequirement(name = "Authorization")
    Flight updateFlight(@PathVariable Long id, @RequestBody FlightDataRequest req);

    @DeleteMapping("/{id}")
    @Operation(summary= "Delete existing flight")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful deleted of flight data", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DeleteRes.class))
            }),
            @ApiResponse(responseCode = "404", description = "Flight not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorRes.class))
            })
    })
    @SecurityRequirement(name = "Authorization")
    Object deleteFlight(@PathVariable Long id);

    @GetMapping("/search")
    @Operation(summary= "Search and get a paginated list of flights")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful retrieval of flight data (if no elements, return an empty array)", useReturnTypeSchema = true)
    })
    @SecurityRequirement(name = "Authorization")
    SearchRes searchFlights(
            @Parameter(description = "Departure Date (YYYY-AA-GG)", required = true) @RequestParam(defaultValue = "2024-02-06") LocalDate departureDate,
            @Parameter(description = "Arrival Date (YYYY-AA-GG)") @RequestParam(defaultValue = "") LocalDate arrivalDate,
            @Parameter(description = "Departure Airport ID", required = true) @RequestParam(defaultValue = "2024-02-06") Long departureAirportId,
            @Parameter(description = "Arrival Airport ID", required = true) @RequestParam(defaultValue = "") Long arrivalAirportId,
            @Parameter(description = "Sort by (default: departureDateTime)", schema = @Schema(allowableValues = {"departureDateTime", "arrivalDateTime", "price"}))
            @RequestParam(required = false, defaultValue = "departureDateTime") String sortBy,
            @Parameter(description = "Sort order (default: asc)", schema = @Schema(allowableValues = {"asc", "desc"}))
            @RequestParam(required = false, defaultValue = "asc") String sortOrder
    );
}
