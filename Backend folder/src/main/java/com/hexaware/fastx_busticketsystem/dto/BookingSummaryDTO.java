package com.hexaware.fastx_busticketsystem.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public class BookingSummaryDTO {
	  private int bookingId;
	    private String status;
	    private LocalDate bookingDate;
	    private String busName;
	    private String busType;      
	    private String busNumber;    
	    private LocalTime departureTime;
	    private LocalTime arrivalTime;
	    private String name;       
	    private List<String> seatsBooked;     
	    private double totalAmount;    

	    
	    public BookingSummaryDTO() {}

	   
   
   

	public BookingSummaryDTO(int bookingId, String status, LocalDate bookingDate, String busName, String busType,
				String busNumber, LocalTime departureTime, LocalTime arrivalTime, String name, List<String> seatsBooked,
				double totalAmount) {
			super();
			this.bookingId = bookingId;
			this.status = status;
			this.bookingDate = bookingDate;
			this.busName = busName;
			this.busType = busType;
			this.busNumber = busNumber;
			this.departureTime = departureTime;
			this.arrivalTime = arrivalTime;
			this.name = name;
			this.seatsBooked = seatsBooked;
			this.totalAmount = totalAmount;
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





	public String getName() {
		return name;
	}





	public void setName(String name) {
		this.name = name;
	}





	public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDate bookingDate) { this.bookingDate = bookingDate; }

    public String getBusName() { return busName; }
    public void setBusName(String busName) { this.busName = busName; }

   

   

    public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public String getBusNumber() {
		return busNumber;
	}

	public void setBusNumber(String busNumber) {
		this.busNumber = busNumber;
	}

	public List<String> getSeatsBooked() {
		return seatsBooked;
	}

	public void setSeatsBooked(List<String> seatsBooked) {
		this.seatsBooked = seatsBooked;
	}

	public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
}
