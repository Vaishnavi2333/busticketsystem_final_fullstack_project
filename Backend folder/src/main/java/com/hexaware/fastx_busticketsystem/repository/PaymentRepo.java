package com.hexaware.fastx_busticketsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.fastx_busticketsystem.entities.Payment;

public interface PaymentRepo extends JpaRepository<Payment,Integer> {
	
	Optional<Payment> findByBooking_BookingId(int bookingId);


}
