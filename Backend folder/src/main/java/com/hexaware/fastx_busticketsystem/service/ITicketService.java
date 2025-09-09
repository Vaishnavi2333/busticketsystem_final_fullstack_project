package com.hexaware.fastx_busticketsystem.service;

import java.util.List;

import com.hexaware.fastx_busticketsystem.dto.BookingDto;
import com.hexaware.fastx_busticketsystem.dto.TicketDto;
import com.hexaware.fastx_busticketsystem.entities.Ticket;
import com.hexaware.fastx_busticketsystem.exception.TicketNotFoundException;
import com.hexaware.fastx_busticketsystem.exception.TripNotFoundException;

public interface ITicketService {
	

    
	public Ticket generateTicketFromBooking(int bookingId);
	
	public void cancelBookingAndTickets(int bookingId) throws TicketNotFoundException;
    
	public Ticket getTicketById(int ticketId) throws TicketNotFoundException;
	
	public List<Ticket> getAllTickets();
    
    List<Ticket> getTicketsByBookingId(int bookingId);
}
