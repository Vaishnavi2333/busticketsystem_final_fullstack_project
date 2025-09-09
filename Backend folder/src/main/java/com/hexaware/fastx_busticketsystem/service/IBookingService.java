package com.hexaware.fastx_busticketsystem.service;

import java.time.LocalDate;
import java.util.List;

import com.hexaware.fastx_busticketsystem.dto.BookingDto;
import com.hexaware.fastx_busticketsystem.dto.BookingSummaryDTO;
import com.hexaware.fastx_busticketsystem.entities.Booking;
import com.hexaware.fastx_busticketsystem.exception.BookingNotFoundException;

public interface IBookingService {
	
	public Booking addBooking(BookingDto bookingDto);

    
    public void cancelBooking(int bookingId) throws BookingNotFoundException;

   
    public BookingDto getBookingById(int bookingId) throws BookingNotFoundException;

    
    public List<Booking> getAllBookings();

    
    public List<Booking> getBookingsByUserId(int userId);

    
    public List<String> getAvailableSeats(int tripId);
    
    List<Booking> getBookingsByOperator(int operatorId);
    String refundBookingByOperator(int bookingId);
    
    public List<BookingSummaryDTO> getBookingSummaryByUserId(int userId);

}
