package com.hexaware.fastx_busticketsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.fastx_busticketsystem.entities.Ticket;

public interface TicketRepo extends JpaRepository<Ticket,Integer>{

	List<Ticket> findByBookingBookingId(int bookingId);
	
	

}
