package com.hexaware.fastx_busticketsystem.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.fastx_busticketsystem.dto.BookingDto;
import com.hexaware.fastx_busticketsystem.dto.TicketDto;
import com.hexaware.fastx_busticketsystem.entities.Booking;
import com.hexaware.fastx_busticketsystem.entities.Payment;
import com.hexaware.fastx_busticketsystem.entities.Ticket;
import com.hexaware.fastx_busticketsystem.exception.TicketNotFoundException;
import com.hexaware.fastx_busticketsystem.exception.TripNotFoundException;
import com.hexaware.fastx_busticketsystem.repository.BookingRepo;
import com.hexaware.fastx_busticketsystem.repository.TicketRepo;


/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:09-Aug-2025
Description:  Ticket Service Implementation Class*/
@Service
public class TicketServiceImpl implements ITicketService{
	
	@Autowired
	TicketRepo repo;
	
	@Autowired
    BookingRepo bookingRepo;

	
	@Override
	public Ticket generateTicketFromBooking(int bookingId) { 
		Booking booking = bookingRepo.findById(bookingId) 
				.orElseThrow(() -> new RuntimeException("Booking not found"));
		if (!booking.getPaymentDone()) { throw new RuntimeException("Payment not completed"); } 
		Ticket ticket = new Ticket(); ticket.setBooking(booking); 
		ticket.setTrip(booking.getTrip()); ticket.setBus(booking.getTrip().getBus());
		ticket.setIssueDate(LocalDate.now()); return repo.save(ticket); 
		}
	
	@Override
	public void cancelBookingAndTickets(int bookingId) throws TicketNotFoundException {
	    Booking booking = bookingRepo.findById(bookingId)
	            .orElseThrow(() -> new RuntimeException("Booking not found"));

	   
	    List<Ticket> tickets = repo.findByBookingBookingId(bookingId);
	    if (tickets.isEmpty()) {
	        throw new TicketNotFoundException("No tickets found for booking ID: " + bookingId);
	    }
	    repo.deleteAll(tickets);

	   
	    bookingRepo.delete(booking);
	}

	@Override
	public Ticket getTicketById(int ticketId) throws TicketNotFoundException {
	    return repo.findById(ticketId)
	            .orElseThrow(() -> new TicketNotFoundException("Ticket not found with id: " + ticketId));
	}
	
	@Override
	public List<Ticket> getTicketsByBookingId(int bookingId) {
	    return repo.findByBookingBookingId(bookingId);
	}


	

	@Override
	public List<Ticket> getAllTickets() {
	    return repo.findAll();
	}

}
