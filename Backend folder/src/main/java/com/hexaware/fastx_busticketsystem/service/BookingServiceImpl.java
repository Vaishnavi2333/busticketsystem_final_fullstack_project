package com.hexaware.fastx_busticketsystem.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.fastx_busticketsystem.dto.BookingDto;
import com.hexaware.fastx_busticketsystem.dto.BookingSummaryDTO;
import com.hexaware.fastx_busticketsystem.entities.Booking;
import com.hexaware.fastx_busticketsystem.entities.Bus;
import com.hexaware.fastx_busticketsystem.entities.Payment;
import com.hexaware.fastx_busticketsystem.entities.Trip;
import com.hexaware.fastx_busticketsystem.entities.UserData;
import com.hexaware.fastx_busticketsystem.exception.BookingNotFoundException;
import com.hexaware.fastx_busticketsystem.repository.BookingRepo;
import com.hexaware.fastx_busticketsystem.repository.BusRepo;
import com.hexaware.fastx_busticketsystem.repository.PaymentRepo;
import com.hexaware.fastx_busticketsystem.repository.TripRepo;
import com.hexaware.fastx_busticketsystem.repository.UserDataRepo;


/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:09-Aug-2025
Description:  Booking Service Implementation Class*/
@Service
public class BookingServiceImpl implements IBookingService {

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private UserDataRepo userRepo;

    @Autowired
    private TripRepo tripRepo;
    
    @Autowired
    BusRepo busRepo;
    
    @Autowired
    PaymentRepo paymentRepo;
    
    
    public List<String> getAllSeats(int capacity) {
        List<String> allSeats = new ArrayList<>();
        for (int i = 1; i <= capacity; i++) {
            allSeats.add("S" + i);
        }
        return allSeats;
    }

    @Override
    public Booking addBooking(BookingDto bookingDto) {
        UserData user = userRepo.findById(bookingDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + bookingDto.getUserId()));

        Trip trip = tripRepo.findById(bookingDto.getTripId())
                .orElseThrow(() -> new RuntimeException("Trip not found with ID: " + bookingDto.getTripId()));

        List<String> bookedSeats = bookingRepo.findByTrip_TripId(trip.getTripId())
                .stream()
                .flatMap(b -> b.getSelectedSeats().stream())
                .collect(Collectors.toList());

        for (String seat : bookingDto.getSelectedSeats()) {
            if (bookedSeats.contains(seat)) {
                throw new RuntimeException("Seat " + seat + " is already booked");
            }
        }

        Booking booking = new Booking();
        booking.setBookingDate(LocalDate.now());      
        booking.setStatus("Pending");                  
        booking.setUser(user);
        booking.setTrip(trip);
        booking.setSelectedSeats(bookingDto.getSelectedSeats());

        double totalPrice = bookingDto.getSelectedSeats().size() * trip.getFare();
        booking.setTotalPrice(totalPrice);            
        booking.setPaymentDone(false);                
        booking.setPayment(null);                     

        return bookingRepo.save(booking);
    }

    @Override
    public void cancelBooking(int bookingId) throws BookingNotFoundException {
       
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with ID: " + bookingId));

       
        booking.setStatus("Cancelled");
        bookingRepo.save(booking);
    }

    @Override
    public BookingDto getBookingById(int bookingId) throws BookingNotFoundException {
    	Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with ID: " + bookingId));
    	BookingDto dto = new BookingDto();
    	
        dto.setBookingId(booking.getBookingId());
        dto.setBookingDate(booking.getBookingDate());
        dto.setStatus(booking.getStatus());
        dto.setUserId(booking.getUser().getUserLogin().getUserId()); 
        dto.setTripId(booking.getTrip().getTripId());
        dto.setTotalPrice(booking.getTotalPrice());
        dto.setSelectedSeats(booking.getSelectedSeats());
        dto.setPaymentDone(booking.getPaymentDone());
        
        Trip trip = booking.getTrip();
        if (trip != null) {
            dto.setDepartureTime(trip.getDepartureTime().toString());
            dto.setArrivalTime(trip.getArrivalTime().toString());

           
            Bus bus = trip.getBus();
            if (bus != null) {
                dto.setBusName(bus.getBusName());
                dto.setBusType(bus.getBusType());
                dto.setBusNumber(bus.getBusNumber());
            }
        }
        
        return dto;
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    @Override
    public List<Booking> getBookingsByUserId(int userId) {
        return bookingRepo.findByUser_UserdataId(userId);
    }

    @Override
    public List<String> getAvailableSeats(int tripId) {
        Trip trip = tripRepo.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        int capacity = trip.getBus().getCapacity();  
        List<String> allSeats = getAllSeats(capacity);

        List<String> bookedSeats = bookingRepo.findByTrip_TripId(tripId)
                .stream()
                .flatMap(b -> b.getSelectedSeats().stream())
                .collect(Collectors.toList());

        allSeats.removeAll(bookedSeats); 
        return allSeats;
    }
    @Override
    public List<Booking> getBookingsByOperator(int operatorId) {
        
        List<Bus> buses = busRepo.findByBusOpData_BusOpLogin_BusOpId(operatorId);

       
        List<Booking> bookings = new ArrayList<>();
        for (Bus bus : buses) {
            List<Trip> trips = tripRepo.findByBusBusId(bus.getBusId());
            for (Trip trip : trips) {
                bookings.addAll(bookingRepo.findByTrip_TripId(trip.getTripId()));
            }
        }

        return bookings;
    }

    @Override
    public String refundBookingByOperator(int bookingId) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + bookingId));

        if (!"Cancelled".equals(booking.getStatus())) {
            throw new IllegalStateException("Only cancelled bookings can be refunded.");
        }

        double amount = 0;
        if (booking.getPayment() != null) {
            amount = booking.getPayment().getAmount();
            booking.getPayment().setStatus("Refunded");
            paymentRepo.save(booking.getPayment());
        }

        
        bookingRepo.delete(booking);

        return "Payment amount: " + amount + " refunded. Booking deleted.";
    }
    
   

    @Override
    public List<BookingSummaryDTO> getBookingSummaryByUserId(int userId) {
        List<Booking> bookings = bookingRepo.findByUser_UserdataId(userId);

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
                        bus != null ? bus.getBusName() : "N/A",
                        bus != null ? bus.getBusType() : "N/A",
                        bus != null ? bus.getBusNumber() : "N/A",
                        b.getTrip().getDepartureTime(),
                        b.getTrip().getArrivalTime(),
                        b.getUser() != null ? b.getUser().getName() : "N/A",
                        seatList,
                        b.getTotalPrice()
                    );
                })
                .collect(Collectors.toList());
    }



}


