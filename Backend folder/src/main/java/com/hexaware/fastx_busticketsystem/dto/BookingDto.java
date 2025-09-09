package com.hexaware.fastx_busticketsystem.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;


/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:08-Aug-2025
Description:Dto Class for Booking*/

@NoArgsConstructor
@Data
public class BookingDto {
	
	private int bookingId;

    @NotNull(message = "Booking date cannot be null")
    @FutureOrPresent(message="Booking date cannot be past")
    private LocalDate bookingDate;

    @NotNull(message = "Booking status cannot be null")
    private String status;

    @NotNull(message = "User ID cannot be null")
    private int userId;

    @NotNull(message = "Trip ID cannot be null")
    private int tripId;

    @Positive(message = "Total price must be positive")
    private double totalPrice;

    @NotNull(message = "Selected seats list cannot be null")
    @Size(min = 1, message = "At least one seat must be selected")
    private List<@Pattern(regexp = "^[A-Z]\\d{1,2}$", message = "Invalid seat format") String> selectedSeats;

    @Positive(message = "Payment ID must be a positive number")
    private Integer paymentId;
    
    @NotNull(message = "Payment status must be specified")
    private Boolean paymentDone;
    
    private String busName;
    
    private String busType;
    
    private String busNumber;
    
    private String departureTime;
    
    private String arrivalTime;

}
