package com.hexaware.fastx_busticketsystem.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:08-Aug-2025
Description:Dto Class for Ticket*/


@NoArgsConstructor
@Data
public class TicketDto {
	
	
	private int ticketId; 

    @NotEmpty(message = "At least one seat must be selected")
    private List<String> selectedSeats;

    @NotNull(message = "Fare cannot be null")
    @DecimalMin(value = "0.01", message = "Fare must be greater than 0")
    private double fare;

    @Positive(message = "Booking ID must be positive")
    private int bookingId;

    @Positive(message = "Trip ID must be positive")
    private int tripId;

    @Positive(message = "Bus ID must be positive")
    private int busId;
    
    @FutureOrPresent(message="Ticket issuedate should not be past")
    private LocalDate issueDate;

}