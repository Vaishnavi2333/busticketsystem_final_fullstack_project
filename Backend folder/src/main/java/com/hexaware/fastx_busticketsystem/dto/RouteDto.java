package com.hexaware.fastx_busticketsystem.dto;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
Author:Vaishnavi Suresh Vaidyanath
Modified Date:08-Aug-2025
Description:Dto Class for Route
*/


@NoArgsConstructor
@Data
public class RouteDto {
	
	   
	    private int routeId;

	    @NotBlank(message = "Route name should be valid and not blank")
	    private String routeName;

	    @NotBlank(message = "Origin cannot be blank")
	    @Size(max = 20, message = "Origin name cannot exceed 20 characters")
	    private String origin;

	    @NotBlank(message = "Destination cannot be blank")
	    @Size(max = 120, message = "Destination name cannot exceed 20 characters")
	    private String destination;

	    @Positive(message = "Distance (in km) must be a positive value")
	    @Digits(integer = 6, fraction = 2, message = "Distance can have up to 6 digits and 2 decimal places")
	    private double distanceKm;

	    @NotNull(message = "Estimated time is required")
	    private LocalTime estimatedTime;
	    
	   
	    
	    

}
