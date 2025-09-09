package com.hexaware.fastx_busticketsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.fastx_busticketsystem.dto.TicketDto;
import com.hexaware.fastx_busticketsystem.entities.Ticket;
import com.hexaware.fastx_busticketsystem.exception.TicketNotFoundException;
import com.hexaware.fastx_busticketsystem.exception.TripNotFoundException;
import com.hexaware.fastx_busticketsystem.service.ITicketService;

import jakarta.validation.Valid;

/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:05-Sept-2025
Description:Controller Class for Ticket*/
@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private ITicketService service;

  
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/generate/{bookingId}")
    public TicketDto generateTicketFromBooking(@PathVariable int bookingId) {
    	Ticket ticket = service.generateTicketFromBooking(bookingId);

        TicketDto dto = new TicketDto();
        dto.setTicketId(ticket.getTicketId());
        dto.setIssueDate(ticket.getIssueDate());
       
        dto.setTripId(ticket.getTrip().getTripId());

        return dto;
    }

   
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/cancel-booking/{bookingId}")
    public String cancelBookingAndTickets(@PathVariable int bookingId) throws TicketNotFoundException {
        service.cancelBookingAndTickets(bookingId);
        return "Booking and associated tickets for booking ID " + bookingId + " have been cancelled successfully.";
    }

    
   
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{ticketId}")
    public Ticket getTicketById(@PathVariable int ticketId) throws TicketNotFoundException {
        return service.getTicketById(ticketId);
    }

    
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/booking/{bookingId}")
    public List<Ticket> getTicketsByBookingId(@PathVariable int bookingId) {
        return service.getTicketsByBookingId(bookingId);
    }

   
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/all")
    public List<Ticket> getAllTickets() {
        return service.getAllTickets();
    }
}