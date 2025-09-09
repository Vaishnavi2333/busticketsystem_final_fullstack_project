package com.hexaware.fastx_busticketsystem.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:07-Aug-2025
Description:Ticket Entity Class*/


@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int ticketId;

   
   
    @Column(name = "seat_number")
    private List<String> selectedSeats = new ArrayList<>();

    private double fare;

    @ManyToOne(optional = false)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne(optional = false)
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bus_id")
    private Bus bus;
    
    private LocalDate issueDate;
    
  
    public Ticket() {}
    
    




	public Ticket(int ticketId, List<String> selectedSeats, double fare, Booking booking, Trip trip, Bus bus,
			LocalDate issueDate) {
		super();
		this.ticketId = ticketId;
		this.selectedSeats = selectedSeats;
		this.fare = fare;
		this.booking = booking;
		this.trip = trip;
		this.bus = bus;
		this.issueDate = issueDate;
	}






	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public List<String> getSelectedSeats() {
		return selectedSeats;
	}

	public void setSelectedSeats(List<String> selectedSeats) {
		this.selectedSeats = selectedSeats;
	}

	public double getFare() {
		return fare;
	}

	public void setFare(double fare) {
		this.fare = fare;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public Bus getBus() {
		return bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}



	public LocalDate getIssueDate() {
		return issueDate;
	}



	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}
	

    

}
