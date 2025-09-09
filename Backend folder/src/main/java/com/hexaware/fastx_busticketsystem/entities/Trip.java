package com.hexaware.fastx_busticketsystem.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;


/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:07-Aug-2025
Description:Trip Entity Class*/


@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Entity
@Table(name="trip")
public class Trip {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int tripId;
    private LocalDate date;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private double fare;
    private String status;
    
   
    
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "route_id")
    @JsonIgnoreProperties({"trips", "buses"})
	private Route route;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bus_id") 
    @JsonIgnore
    private Bus bus;
    
    @JsonBackReference
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();
    
   

    public Bus getBus() {
		return bus;
	}
	public void setBus(Bus bus) {
		this.bus = bus;
	}
	public Trip() {
    	
    }
	public Trip(int tripId,  LocalDate date, LocalTime departureTime, LocalTime arrivalTime,
			double fare, String status) {
		super();
		this.tripId = tripId;
		this.date = date;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.fare = fare;
		this.status = status;
	}
	public int getTripId() {
		return tripId;
	}
	public void setTripId(int tripId) {
		this.tripId = tripId;
	}
	
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public LocalTime getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(LocalTime departureTime) {
		this.departureTime = departureTime;
	}
	public LocalTime getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(LocalTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public double getFare() {
		return fare;
	}
	public void setFare(double fare) {
		this.fare = fare;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}
	public List<Booking> getBookings() {
	    return bookings;
	}

	public void setBookings(List<Booking> bookings) {
	    this.bookings = bookings;
	}
	
	public void addBooking(Booking booking) {
	    bookings.add(booking);
	    booking.setTrip(this);
	}

	public void removeBooking(Booking booking) {
	    bookings.remove(booking);
	    booking.setTrip(null);
	}
	
	
	@JsonProperty("busId")
	public int getBusId() {
	    return bus != null ? bus.getBusId() : 0;
	}
	
	@Transient
	@JsonProperty("busName")
	public String getBusName() {
	    return bus != null ? bus.getBusName() : null;
	}

}
