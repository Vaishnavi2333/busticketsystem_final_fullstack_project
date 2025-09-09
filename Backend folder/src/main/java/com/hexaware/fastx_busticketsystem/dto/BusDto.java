package com.hexaware.fastx_busticketsystem.dto;

import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;



/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:08-Aug-2025
Description:Dto Class for Bus
*/

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
@Data
public class BusDto {
	
	
	 private int busId;

	@Pattern(regexp="[1-9][0-9]{2}")
	 private String busNumber;
	
	 @NotNull(message = "Bus name cannot be null")
	 private String busName;
	
	 @NotNull(message = "Bus type cannot be null")
	 private String busType;
	
	 @Positive(message = "Capacity must be a positive number")
     private int capacity;
	 
     @Pattern(regexp = "Available|Unavailable",message="Status should be either available or unavailable")
     private String status;
     
     private Integer routeId;
     
     private List<Integer> tripIds;
     
     private List<TripDto> trips; 
     
     private Integer tripId;
     

     private double fare;

    
     private LocalTime departureTime;

    
     private LocalTime arrivalTime;
     
 
     private List<BusAmenityDto> amenities;
    
    

     
}
