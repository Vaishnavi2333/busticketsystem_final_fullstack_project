package com.hexaware.fastx_busticketsystem.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.hexaware.fastx_busticketsystem.entities.Trip;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:08-Aug-2025
Description:Dto Class for Trip
*/
@NoArgsConstructor
@Data
public class TripDto {

   
    private int tripId;

    @NotNull(message = "Trip date is required")
    @Future(message = "Date must be future")
    private LocalDate date;

    @NotNull(message = "Departure time is required")
    private LocalTime departureTime;

    @NotNull(message = "Arrival time is required")
    private LocalTime arrivalTime;

    @Positive(message = "Fare must be greater than zero")
    private double fare;

    @NotBlank(message = "Status is required")
    private String status;

    @Positive(message = "Bus ID must be positive")
    private int busId;

    @Positive(message = "Route ID must be positive")
    private int routeId;
    
    
    public TripDto(Trip trip) {
    	this.tripId = trip.getTripId();
        this.date = trip.getDate();
        this.departureTime = trip.getDepartureTime();
        this.arrivalTime = trip.getArrivalTime();
        this.fare = trip.getFare();
        this.status = trip.getStatus();
        this.busId = trip.getBus() != null ? trip.getBus().getBusId() : 0;
        this.routeId = trip.getRoute() != null ? trip.getRoute().getRouteId() : 0;
        
    }
}