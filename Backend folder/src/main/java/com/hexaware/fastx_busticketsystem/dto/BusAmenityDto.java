package com.hexaware.fastx_busticketsystem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:08-Aug-2025
Description:Dto Class for BusAmenity
*/


@NoArgsConstructor
@Data
public class BusAmenityDto {
 

	private int busamenityId; 
	
    @Positive(message = "Bus ID should be positive value")
    private int busId;  
    
    
    @NotNull(message = "Amenity name cannot be null")
    private String amenityName;
}

