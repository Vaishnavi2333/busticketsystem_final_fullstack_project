package com.hexaware.fastx_busticketsystem.dto;

import java.time.LocalDate;

import com.hexaware.fastx_busticketsystem.entities.Booking;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
Author:Vaishnavi Suresh Vaidyanath
Modified Date:08-Aug-2025
Description:Dto Class for Payment
*/

@NoArgsConstructor
@Data
public class PaymentDto {


     private int bookingId;
	
	 @NotNull(message = "Amount is required")
	 @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
	 private double amount;
	 
	 @FutureOrPresent(message="Payment Date cannot be past")
	 private LocalDate paymentDate;
	 
	 @Pattern(regexp = "Cash|Card|UPI|NetBanking",message = "Payment method must be Cash, Card, UPI, or NetBanking")
	 private String paymentMethod;
	 
	 @Pattern(regexp = "Paid|Pending")
	 private String status;
	 
	

}
