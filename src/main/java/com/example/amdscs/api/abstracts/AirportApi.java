package com.example.amdscs.api.abstracts;

import com.example.amdscs.dto.requests.AirportDataRequest;
import com.example.amdscs.dto.responses.DeleteRes;
import com.example.amdscs.models.Airport;
import com.example.amdscs.dto.responses.ErrorRes;
import com.example.amdscs.dto.responses.PageRes;
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

@Tag(name = "Airports", description = "Airport API")
public interface AirportApi {
    @GetMapping
    @Operation(summary= "Get a paginated list of airports")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful retrieval of airport data (if no elements, return an empty array)", useReturnTypeSchema = true)
    })
    @SecurityRequirement(name = "Authorization")
    PageRes<Airport> getAirports(
            @Parameter(description = "Page") @RequestParam(required = false, defaultValue = "0") int page,
            @Parameter(description = "Limit") @RequestParam(required = false, defaultValue = "5") int limit,
            @Parameter(description = "Sort by (default: departureDateTime)", schema = @Schema(allowableValues = {"id", "price", "departureDateTime", "arrivalDateTime"}))
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @Parameter(description = "Sort order (default: asc)", schema = @Schema(allowableValues = {"asc", "desc"}))
            @RequestParam(required = false, defaultValue = "asc") String sortOrder
    );

    @GetMapping("/{id}")
    @Operation(summary= "Get airport by ID", description= "Airport must exist")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful retrieval of airport data", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "Airport not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorRes.class))
            })
    })
    @SecurityRequirement(name = "Authorization")
    Airport getAirport(@PathVariable Long id);

    @PostMapping
    @Operation(summary= "Create a new airport")
    @SecurityRequirement(name = "Authorization")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Successful created of airport data", useReturnTypeSchema = true),
    })
    @SecurityRequirement(name = "Authorization")
    Airport createAirport(@RequestBody AirportDataRequest req);

    @PutMapping("/{id}")
    @Operation(summary = "Update existing airport")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful updated of airport data", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "Airport not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorRes.class))
            })
    })
    @SecurityRequirement(name = "Authorization")
    Airport updateAirport(@PathVariable Long id, @RequestBody AirportDataRequest req);

    @DeleteMapping("/{id}")
    @Operation(summary= "Delete existing airport")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful deleted of airport data", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DeleteRes.class))
            }),
            @ApiResponse(responseCode = "404", description = "Airport not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorRes.class))
            })
    })
    @SecurityRequirement(name = "Authorization")
    Object deleteAirport(@PathVariable Long id);
}
