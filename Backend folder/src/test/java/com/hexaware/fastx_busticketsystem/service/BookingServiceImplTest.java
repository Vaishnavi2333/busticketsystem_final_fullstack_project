package com.hexaware.fastx_busticketsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.fastx_busticketsystem.dto.BookingDto;
import com.hexaware.fastx_busticketsystem.entities.Booking;
import com.hexaware.fastx_busticketsystem.entities.Payment;
import com.hexaware.fastx_busticketsystem.repository.BookingRepo;
import com.hexaware.fastx_busticketsystem.repository.PaymentRepo;
import com.hexaware.fastx_busticketsystem.repository.TripRepo;
import com.hexaware.fastx_busticketsystem.repository.UserDataRepo;

import lombok.extern.slf4j.Slf4j;


/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:14-Aug-2025
Description:  BookingService Class test case*/

@SpringBootTest
@Slf4j
public class BookingServiceImplTest {

    @Autowired
    private IBookingService bookingService;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private TripRepo tripRepo;

    @Autowired
    private UserDataRepo userRepo;

    @Autowired
    private PaymentRepo paymentRepo;

    @Test
    void testAddBooking() {
        BookingDto dto = new BookingDto();
        dto.setUserId(1); 
        dto.setTripId(1); 
        dto.setSelectedSeats(Arrays.asList("S1", "S2"));

        Booking booking = bookingService.addBooking(dto);
        log.info("Booking added: ", booking);

        assertNotNull(booking);
        assertEquals("Pending", booking.getStatus());
        assertEquals(dto.getSelectedSeats().size() * booking.getTrip().getFare(), booking.getTotalPrice());
    }

    @Test
    void testGetAvailableSeats() {
        int tripId = 1; 
        List<String> availableSeats = bookingService.getAvailableSeats(tripId);
        log.info("Available seats for trip  ", tripId, availableSeats);

        assertNotNull(availableSeats);
        assertTrue(availableSeats.size() > 0);
    }

    @Disabled
    @Test
    void testRefundBookingByOperator() {
        int bookingId = 1; 
        bookingService.refundBookingByOperator(bookingId);

        Booking booking = bookingRepo.findById(bookingId).orElseThrow();
        log.info("Booking after refund: {}", booking);

        assertEquals("Cancelled", booking.getStatus());
        if (booking.getPayment() != null) {
            Payment payment = booking.getPayment();
            log.info("Payment after refund ", payment);
            assertEquals("Refunded", payment.getStatus());
        }
    }
}
	


