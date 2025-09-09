package com.hexaware.fastx_busticketsystem.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.hexaware.fastx_busticketsystem.dto.BookingDto;
import com.hexaware.fastx_busticketsystem.dto.BookingSummaryDTO;
import com.hexaware.fastx_busticketsystem.entities.Booking;
import com.hexaware.fastx_busticketsystem.entities.Bus;
import com.hexaware.fastx_busticketsystem.exception.BookingNotFoundException;
import com.hexaware.fastx_busticketsystem.service.IBookingService;

import jakarta.validation.Valid;


/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:05-Sept-2025
Description:Controller class for Booking*/


@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private IBookingService service;

   
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add")
    public Map<String, Object> addBooking(@Valid @RequestBody BookingDto bookingDto) {
    	 Booking booking = service.addBooking(bookingDto);

    	   
    	    Map<String, Object> response = new HashMap<>();
    	    response.put("bookingId", booking.getBookingId());
    	    response.put("totalPrice", booking.getTotalPrice());
    	    response.put("status", booking.getStatus());
    	    response.put("selectedSeats", booking.getSelectedSeats());

    	    return response;
    }

   
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/cancel/{bookingId}")
    public String cancelBooking(@PathVariable int bookingId) throws BookingNotFoundException {
        service.cancelBooking(bookingId);
        return "Booking with ID " + bookingId + " has been cancelled.";
    }

   
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/get/{bookingId}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable int bookingId) throws BookingNotFoundException {
        BookingDto bookingDto = service.getBookingById(bookingId); 
        if (bookingDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(bookingDto);
    }

   
    @PreAuthorize("hasAnyRole('USER','ADMIN','BUS_OPERATOR')")
    @GetMapping("/user/{userId}")
    public List<Booking> getBookingsByUser(@PathVariable int userId) {
        return service.getBookingsByUserId(userId);
    }

    
   
    @PostMapping("/book-seat")
    @PreAuthorize("hasRole('USER')")
    public Booking bookSeats(@RequestBody BookingDto bookingDto) {
        return service.addBooking(bookingDto);
    }

   
    @PreAuthorize("hasRole('BUS_OPERATOR')")
    @GetMapping("/operator/{operatorId}")
    public List<Booking> getBookingsByOperator(@PathVariable int operatorId) {
        return service.getBookingsByOperator(operatorId);
    }

   
    @PreAuthorize("hasRole('BUS_OPERATOR')")
    @PutMapping("/refund/{bookingId}")
    public String refundBookingByOperator(@PathVariable int bookingId) {
        try {
          
            String message = service.refundBookingByOperator(bookingId);
            return message;  
        
        } catch (IllegalStateException e) {
            return "Error: " + e.getMessage();  
        }
    }
   
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/{userId}/summary")
    public List<BookingSummaryDTO> getBookingSummary(@PathVariable int userId) {
        return service.getBookingSummaryByUserId(userId);
    }
    @PreAuthorize("hasAnyRole('ADMIN','BUS_OPERATOR')")
    @GetMapping("/bookings/summary")
    public List<BookingSummaryDTO> getAllBookingSummaries() {
        List<Booking> bookings = service.getAllBookings();
        return bookings.stream()
                .map(b -> {
                    List<String> seatList = b.getSelectedSeats() != null
                            ? new ArrayList<>(b.getSelectedSeats())
                            : new ArrayList<>();

                    Bus bus = b.getTrip().getBus(); 

                    return new BookingSummaryDTO(
                            b.getBookingId(),
                            b.getStatus(),
                            b.getBookingDate(),
                            bus.getBusName(),        
                            bus.getBusType(),        
                            bus.getBusNumber(),      
                            b.getTrip().getDepartureTime(),  
                            b.getTrip().getArrivalTime(),    
                            b.getUser().getName(),           
                            seatList,                        
                            b.getTotalPrice()                
                    );
                })
                .sorted((b1, b2) -> Long.compare(b2.getBookingId(), b1.getBookingId()))
                .collect(Collectors.toList());
    }



    
    @PreAuthorize("hasAnyRole('ADMIN','BUS_OPERATOR')")
    @GetMapping("/bookings")
    public List<Booking> getAllBookings() {
        return service.getAllBookings();
    }
}